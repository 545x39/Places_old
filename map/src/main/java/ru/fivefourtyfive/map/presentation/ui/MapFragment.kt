package ru.fivefourtyfive.map.presentation.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.osmdroid.api.IGeoPoint
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.getDistance
import ru.fivefourtyfive.map.presentation.viewmodel.MapEvent
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.places.Places
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Parameter.ID
import ru.fivefourtyfive.places.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.places.framework.presentation.ui.MainActivity
import ru.fivefourtyfive.places.framework.presentation.ui.NavFragment
import ru.fivefourtyfive.places.framework.presentation.abstraction.IEventDispatcher
import ru.fivefourtyfive.places.util.*
import ru.fivefourtyfive.places.util.PermissionsUtil.isPermissionGranted
import timber.log.Timber
import javax.inject.Inject
import ru.fivefourtyfive.places.R as appR

@Suppress("SpellCheckingInspection")

    //<editor-fold defaultstate="collapsed" desc="CONSTANTS">
private const val AUTO_ZOOM_IN_LEVEL = 16
private const val AUTO_ZOOM_OUT_LEVEL = 14
private const val ZOOM_ANIMATION_SPEED = 300L
    //</editor-fold>

class MapFragment : NavFragment(), IEventDispatcher<MapEvent>, LocationListener {

    //<editor-fold defaultstate="collapsed" desc="FIELDS">
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    @Inject
    lateinit var mapView: MapView

    @Inject
    lateinit var rotationOverlay: RotationGestureOverlay

    @Inject
    lateinit var scaleOverlay: ScaleBarOverlay

    private lateinit var placeTitle: TextView

    private lateinit var placeTitleButton: RelativeLayout

    private lateinit var bearingButton: ImageButton

    private lateinit var centerButton: ImageButton

    private lateinit var progress: ProgressBar

    private var currentMode = MapMode.SCHEME

    private lateinit var locationManager: LocationManager
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LIFECYCLE METHODS">
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerMapFragmentComponent.factory()
            .create((requireActivity().application as Places).appComponent)
            .inject(this)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.app_name)
        setHasOptionsMenu(true)
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        viewModel = ViewModelProvider(this, providerFactory)[MapFragmentViewModel::class.java]
        (requireActivity() as MainActivity).switchKeepScreenOn(viewModel.isKeepScreenOnEnabled())
        view.apply {
            mapView.setMapListener()
            findViewById<FrameLayout>(R.id.map_placeholder).addView(mapView)
            progress = findViewById(R.id.progress)
            placeTitle = findViewById(R.id.place_title)
            placeTitleButton = findViewById(R.id.place_title_button)
            bearingButton = findViewById(R.id.bearing_button)
            centerButton = findViewById(R.id.center_button)
        }
        subscribeToButtonClicks()
        subscribeObservers()
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        setMap()
        viewModel.myLocation.enableMyLocation()
        centerAndZoom()
        mapView.onResume()
        switchSelection(viewModel.currentSelection)
        locationManager.getProviders(true).map {
            isPermissionGranted(requireContext(), ACCESS_FINE_LOCATION)
                .ifTrue { locationManager.requestLocationUpdates(it, 1000, 5.0f, this) }
        }
        getArea(true)
    }

    override fun onPause() {
        super.onPause()
        isPermissionGranted(requireContext(), ACCESS_FINE_LOCATION)
            .ifTrue { locationManager.removeUpdates(this) }
        viewModel.myLocation.disableMyLocation()
        mapView.apply {
            overlays.clear()
            onPause()
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETUP METHODS">
    private fun setMap() {
        with(viewModel) {
            progress.visibility = GONE
            mapView.setTileSource(getTileSource())
            mapView.overlays.apply {
                clear()
                add(rotationOverlay)
                add(transportationOverlay)
                add(imageryLabelsOverlay)
                add(folder)
                add(wikimapiaOverlay)
                add(gridOverlay)
                add(scaleOverlay)
                add(myLocation)
            }
            switchMode()
            gridOverlay.isEnabled = isGridEnabled()
            scaleOverlay.isEnabled = isScaleEnabled()
            switchFollowLocation(isFollowLocationEnabled())
        }
    }

    fun switchSelection(selection: Pair<Int, String>?) {
        with(viewModel) {
            setHighlighted(viewModel.currentSelection.first, false)
            currentSelection = selection ?: -1 to ""
            val (id, title) = currentSelection
            when (id < 0) {
                true -> placeTitleButton.visibility = GONE
                false -> {
                    setHighlighted(id, true)
                    placeTitle.apply {
                        text = title
                        setOnClickListener {
                            navigate(
                                appR.id.action_mapFragment_to_placeDetailsFragment,
                                bundleOf(ID to id)
                            )
                        }
                    }
                    placeTitleButton.visibility = VISIBLE
                }
            }
        }
    }

    private fun subscribeObservers() {

        //<editor-fold defaultstate="collapsed" desc="ON STATE CHANGE METHODS">
        fun onSuccess() {
            runCatching {
                viewModel.folder.items.map {
                    (it as PlacePolygon).setOnClickListener(
                        PlaceOnClickListener(it)
                    )
                }
                mapView.invalidate()
            }
        }

        fun onError(message: String?) =
            (requireActivity() as MainActivity).showSnackBar(message)
        //</editor-fold>

        viewModel.liveData.observe(viewLifecycleOwner) {
            with(it) {
                progress.visibility = progressVisibility
                when (this) {
                    is MapViewState.DataLoaded -> onSuccess()
                    is MapViewState.Error -> onError(message)
                    else -> {
                    }
                }
            }
        }
    }

    private fun subscribeToButtonClicks() {
        bearingButton.clicks()
            .throttleFirst(200)
            .map { onBearingButtonClick() }
            .launchIn(lifecycleScope)
        centerButton.apply {
            clicks()
                .throttleFirst(200)
                .map { onCenterButtonClick() }
                .launchIn(lifecycleScope)
            longClicks()
                .map { onCenterButtonLongClick() }
                .launchIn(lifecycleScope)
        }
    }

    private fun MapView.setMapListener() {
        fun getAndUpdate() {
            getArea()
            updateLastLocationAndZoom()
        }
        addMapListener(DelayedMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?) = true.also {
                getAndUpdate()
            }

            override fun onZoom(event: ZoomEvent?) = true.also {
                getAndUpdate()
            }
        }, viewModel.mapListenerDelay))
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ON CLICK FUNCTIONS">
    private fun onBearingButtonClick() =
        mapView.controller.animateTo(mapView.mapCenter, mapView.zoomLevelDouble, 600, 0.0f)

    private fun onCenterButtonClick() = when (viewModel.isFollowLocationEnabled()) {
        true -> {
            when (mapView.zoomLevelDouble < AUTO_ZOOM_IN_LEVEL) {
                true -> mapView.controller.zoomTo(AUTO_ZOOM_IN_LEVEL, ZOOM_ANIMATION_SPEED)
                false -> mapView.controller.zoomTo(AUTO_ZOOM_OUT_LEVEL, ZOOM_ANIMATION_SPEED)
            }
        }
        false -> {
            viewModel.setFollowLocation(true)
            switchFollowLocation(true)
            mapView.controller.zoomTo(AUTO_ZOOM_IN_LEVEL, ZOOM_ANIMATION_SPEED)
        }
    }

    private fun onCenterButtonLongClick() = true.also {
        viewModel.apply {
            isFollowLocationEnabled().ifTrue {
                setFollowLocation(false)
                (requireActivity() as MainActivity).showSnackBar(resources.getString(appR.string.following_location_disabled))
            }
        }
    }

    inner class PlaceOnClickListener(private val place: PlacePolygon) : Polygon.OnClickListener {
        override fun onClick(polygon: Polygon?, mapView: MapView?, eventPos: GeoPoint?): Boolean {

            //<editor-fold defaultstate="collapsed" desc="INNER FUNCTIONS">
            fun onSame() {
                switchSelection(null)
            }

            fun onDifferent() {
                place.apply {
                    switchSelection(id to title)
                }
                mapView?.apply {
                    viewModel.isCenterSelectionEnabled()
                        .ifTrue { controller.animateTo(GeoPoint(place.lat, place.lon)) }
                }
            }
            //</editor-fold>

            when (place.id == viewModel.currentSelection.first) {
                true -> onSame()
                false -> onDifferent()
            }
            mapView?.invalidate()
            return true
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OPTIONS MENU METHODS">
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_map, menu)

    override fun onPrepareOptionsMenu(menu: Menu) {
        fun item(id: Int) = menu.findItem(id)
        super.onPrepareOptionsMenu(menu)
        with(viewModel) {
            item(R.id.action_wikimapia_overlays).isChecked = wikimapiaOverlaysEnabled()
            item(R.id.action_follow_location).isChecked = isFollowLocationEnabled()
            item(R.id.action_center_selection).isChecked = isCenterSelectionEnabled()
            item(R.id.action_show_scale).isChecked = isScaleEnabled()
            item(R.id.action_show_grid).isChecked = isGridEnabled()
            item(R.id.action_keep_screen_on).isChecked = isKeepScreenOnEnabled()
            item(R.id.action_auto_rotate).isChecked = isAutoRotateMapEnabled()
            when (getMapMode()) {
                MapMode.SCHEME -> item(R.id.action_map_mode_scheme).setChecked(true)
                MapMode.SATELLITE -> item(R.id.action_map_mode_satellite).setChecked(true)
                else -> item(R.id.action_map_mode_scheme).setChecked(true)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_map_mode_scheme -> {
                item.isChecked = true
                dispatchEvent(MapEvent.SwitchMapModeEvent(MapMode.SCHEME))
                mapView.setTileSource(viewModel.getTileSource())
            }
            R.id.action_map_mode_satellite -> {
                item.isChecked = true
                dispatchEvent(MapEvent.SwitchMapModeEvent(MapMode.SATELLITE))
                mapView.setTileSource(viewModel.getTileSource())
            }
            R.id.action_wikimapia_overlays -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchWikimapiaOverlayEvent(item.isChecked))
                switchMode()
                getArea()
            }
            R.id.action_follow_location -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchFollowLocationEvent(item.isChecked))
                switchFollowLocation(item.isChecked)
            }
            R.id.action_center_selection -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchCenterSelectionEvent(item.isChecked))
            }
            R.id.action_show_scale -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchScaleEvent(item.isChecked))
                scaleOverlay.isEnabled = item.isChecked
                mapView.invalidate()
            }
            R.id.action_show_grid -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchGridEvent(item.isChecked))
                viewModel.gridOverlay.isEnabled = item.isChecked
                mapView.invalidate()
            }
            R.id.action_keep_screen_on -> {
                item.isChecked = item.isChecked.not()
                viewModel.setKeepScreenOn(item.isChecked)
                (requireActivity() as MainActivity).switchKeepScreenOn(item.isChecked)
            }
            R.id.action_auto_rotate -> {
                item.isChecked = item.isChecked.not()
                viewModel.setAutoRotateMap(item.isChecked)
            }
            R.id.action_search -> navigate(appR.id.action_mapFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchEvent(event: MapEvent) = viewModel.handleEvent(event)
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MAP FUNCTIONALITY METHODS">
    private fun getArea(force: Boolean = false): Boolean {

        fun isFarEnough(point1: IGeoPoint, point2: IGeoPoint) = getDistance(point1, point2) > 5
//        lifecycleScope.launch {  }
        with(viewModel) {
            mapView.let {
                (force || (wikimapiaOverlaysEnabled() && isFarEnough(
                    it.mapCenter,
                    getLastLocation()
                ) && it.zoomLevelDouble >= 10.0)).ifTrue {
                    dispatchEvent(
                        MapEvent.GetAreaEvent(
                            it.boundingBox.latNorth,
                            it.boundingBox.lonEast,
                            it.boundingBox.latSouth,
                            it.boundingBox.lonWest
                        )
                    )
                }
            }
        }
        return true
    }

    private fun updateLastLocationAndZoom() {
        with(mapView) {
            viewModel.apply {
                setLastLocation(mapCenter.latitude, mapCenter.longitude)
                setLastZoom(zoomLevelDouble)
            }
        }
    }

    private fun switchMode() {
        viewModel.apply {
            transportationOverlay.isEnabled = !wikimapiaOverlaysEnabled()
            imageryLabelsOverlay.isEnabled = !wikimapiaOverlaysEnabled()
            folder.isEnabled = wikimapiaOverlaysEnabled()
            wikimapiaOverlay.isEnabled = wikimapiaOverlaysEnabled()
        }
        mapView.invalidate()
    }

    @SuppressLint("MissingPermission")
    private fun switchFollowLocation(enable: Boolean) {
        with(viewModel) {
            when (enable) {
                true -> {
                    myLocation.enableFollowLocation()
                    myLocation.runOnFirstFix {
                        MainScope().launch {
                            LocationManager.GPS_PROVIDER.apply {
                                (isPermissionGranted(
                                    requireContext(),
                                    ACCESS_FINE_LOCATION
                                ) && locationManager.isProviderEnabled(this)).ifTrue {
                                    locationManager.getLastKnownLocation(this)?.let {
                                        mapView.controller.animateTo(
                                            it.latitude.toInt(),
                                            it.longitude.toInt()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                false -> myLocation.disableFollowLocation()
            }
            mapView.invalidate()
        }
    }

    private fun centerAndZoom() {
        mapView.controller.apply {
            setCenter(viewModel.getLastLocation())
            setZoom(viewModel.getLastZoom())
        }
    }

    override fun onLocationChanged(location: Location) {
        location.bearing.let { it.equals(0.0f).ifFalse { viewModel.latestBearing = it } }
        GeoPoint(location).apply {
            (viewModel.isFollowLocationEnabled()).ifTrue {
                mapView.controller.animateTo(
                    this, mapView.zoomLevelDouble, 600,
                    when (viewModel.isAutoRotateMapEnabled()) {
                        true -> -viewModel.latestBearing
                        false -> mapView.mapOrientation
                    }
                )
            }
        }
    }
    //</editor-fold>
}
