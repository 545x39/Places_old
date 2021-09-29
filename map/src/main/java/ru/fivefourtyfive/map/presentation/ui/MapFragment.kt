package ru.fivefourtyfive.map.presentation.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.ui.overlay.PlaceLabel
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MapMode
import ru.fivefourtyfive.map.presentation.viewmodel.MapEvent
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.Places
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.NavFragment
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventDispatcher
import ru.fivefourtyfive.wikimapper.util.ifFalse
import ru.fivefourtyfive.wikimapper.util.ifTrue
import ru.fivefourtyfive.wikimapper.util.parallelMap
import javax.inject.Inject
import kotlin.math.roundToLong
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : NavFragment(), EventDispatcher<MapEvent>, LocationListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    @Inject
    lateinit var mapView: MapView

    private lateinit var placeTitle: TextView

    private lateinit var progress: ProgressBar

    private var currentSelection: PlacePolygon? = null

    private var currentMode = MapMode.SCHEME

    private lateinit var locationManager: LocationManager

    private fun updatePositionAndGetArea(force: Boolean = false): Boolean {

        fun isLocationTheSame() =
            viewModel.getLastLocation().first != mapView.mapCenter.latitude
                    || viewModel.getLastLocation().second != mapView.mapCenter.longitude

        with(viewModel) {
            mapView.let { map ->
                setLastLocation(map.mapCenter.latitude, map.mapCenter.longitude)
                setLastZoom(map.zoomLevelDouble)
                val get = force || (wikimapiaOverlaysEnabled() && !isLocationTheSame())
                get.ifTrue {
                    getArea(
                        map.boundingBox.lonWest,
                        map.boundingBox.latSouth,
                        map.boundingBox.lonEast,
                        map.boundingBox.latNorth
                    )
                }
            }
        }
        return true
    }

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
        viewModel = ViewModelProvider(this, providerFactory).get(MapFragmentViewModel::class.java)
        view.apply {
            mapView.setListener()
            findViewById<FrameLayout>(R.id.map_placeholder).addView(mapView)
            progress = findViewById(R.id.progress)
            placeTitle = findViewById(R.id.place_title)
        }
        subscribeObserver()
    }

    private fun MapView.setListener() {
        addMapListener(DelayedMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?) = updatePositionAndGetArea()

            override fun onZoom(event: ZoomEvent?) = updatePositionAndGetArea()
        }, viewModel.mapListenerDelay))
    }

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
            switchFollowLocation(viewModel.isFollowLocationEnabled())
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

    private fun switchFollowLocation(enable: Boolean) {
        with(viewModel) {
//            myLocation.enableAutoStop = !enable
//            rotationOverlay.isEnabled = !enable
            when (enable) {
                true -> {
                    myLocation.enableFollowLocation()
                    val (lat, lon) = viewModel.getLastLocation()
                    myLocation.runOnFirstFix {
                        MainScope().launch {
                            mapView.controller.animateTo(lat.toInt(), lon.toInt())
                        }
                    }
                }
                false -> myLocation.disableFollowLocation()
            }
            mapView.invalidate()
        }
    }

    private fun centerAndZoom() {
        val (lat, lon) = viewModel.getLastLocation()
        mapView.controller.apply {
            setCenter(GeoPoint(lat, lon))
            setZoom(viewModel.getLastZoom())
        }
    }

    private fun subscribeObserver() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            with(it) {
                progress.visibility = progressVisibility
                when (this) {
                    is MapViewState.DataLoaded -> onSuccess(places)
                    is MapViewState.Error -> onError(message)
                    else -> {
                    }
                }
            }
        })
    }

    private fun onSuccess(newPlaces: ArrayList<PlacePolygon>) {
        CoroutineScope(Default).launch {
            val itemsToRemove = arrayListOf<PlacePolygon>()
            with(viewModel) {
                folder.items.map {
                    with(it as PlacePolygon) {
                        newPlaces.contains(this).ifFalse { itemsToRemove.add(this) }
                    }
                }
                folder.items.removeAll(itemsToRemove)
                itemsToRemove.clear()
                newPlaces.map { folder.items.contains(it).ifTrue { itemsToRemove.add(it) } }
                newPlaces.removeAll(itemsToRemove)
                folder.items.apply {
                    addAll(newPlaces)
                    map { (it as PlacePolygon).setOnClickListener(PlaceOnClickListener(it)) }
                }
                withContext(Main) { mapView.invalidate() }
            }
        }
    }

    private suspend fun filLabels() {
        with(viewModel) {
            folder.items.apply {
                parallelMap { place ->
                    (place as PlacePolygon).haveToShowLabel(mapView).ifTrue {
                        labels.add(
                            PlaceLabel(
                                place.id,
                                place.lat,
                                place.lon,
                                place.title
                            )
                        )
                    }
                }
            }
        }
    }

    private fun PlacePolygon.hasToShowLabel(): Boolean {
        mapView.boundingBox.apply {
            val widthDiff = (east - west) / (lonEast - lonWest)
            val heightDiff = (north - south) / (latNorth - latSouth)
            val isWithinTheBox =
                (east - west) <= (lonEast - lonWest) && (north - south) <= (latNorth - latSouth)
            return isWithinTheBox && (widthDiff >= 0.3 || heightDiff >= 0.3)
        }
    }

    private fun onError(message: String?) =
        (requireActivity() as MainActivity).showSnackBar(message)

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
                updatePositionAndGetArea()
            }
            R.id.action_follow_location -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchFollowLocationEvent(item.isChecked))
                switchFollowLocation(item.isChecked)
            }
            R.id.action_center_selection -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchCenterSelectionEvent(item.isChecked))
                //TODO implement it!
            }
            R.id.action_show_scale -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchScaleEvent(item.isChecked))
                viewModel.scaleOverlay.isEnabled = item.isChecked
                mapView.invalidate()
            }
            R.id.action_show_grid -> {
                item.isChecked = item.isChecked.not()
                dispatchEvent(MapEvent.SwitchGridEvent(item.isChecked))
                viewModel.gridOverlay.isEnabled = item.isChecked
                mapView.invalidate()
            }
            R.id.action_search -> navigate(appR.id.action_mapFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchEvent(event: MapEvent) = viewModel.handleEvent(event)

    override fun onResume() {
        super.onResume()
        setMap()
        viewModel.myLocation.enableMyLocation()
        centerAndZoom()
        mapView.onResume()
        locationManager.getProviders(true).map {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    ACCESS_FINE_LOCATION
                ) == PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(it, 1500, 5.0f, this)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED
        ) {
            locationManager.removeUpdates(this)
        }
        viewModel.myLocation.disableMyLocation()
        mapView.apply {
            overlays.clear()
            onPause()
        }
    }

    inner class PlaceOnClickListener(private val place: PlacePolygon) : Polygon.OnClickListener {
        override fun onClick(polygon: Polygon?, mapView: MapView?, eventPos: GeoPoint?): Boolean {
            currentSelection?.let { if (it != place) currentSelection?.setHighlighted(false) }
            currentSelection = place
            when (place.highlight) {
                true -> navigate(
                    appR.id.action_mapFragment_to_placeDetailsFragment,
                    bundleOf(ID to place.id)
                )
                false -> placeTitle.text = currentSelection?.title ?: ""
            }
            place.setHighlighted(place.highlight.not())
            this@MapFragment.mapView.apply {
                viewModel.isCenterSelectionEnabled()
                    .ifTrue { controller.animateTo(place.lat.toInt(), place.lon.toInt()) }
                invalidate()
            }
            return true
        }
    }

    override fun onLocationChanged(location: Location) {
        GeoPoint(location).apply {
            //* 3.6 means meters per second to km per hour conversion.
            val speed = (location.speed).roundToLong()
            (viewModel.isFollowLocationEnabled() /*&& speed >= 40*/).ifTrue {
                mapView.controller.animateTo(this, mapView.zoomLevelDouble, 600, -location.bearing)
            }
        }
    }
}
