package ru.fivefourtyfive.map.presentation.ui

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.api.IGeoPoint
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.ui.overlay.PlaceLabel
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.*
import ru.fivefourtyfive.map.presentation.util.MapUtil.addFolder
import ru.fivefourtyfive.map.presentation.util.MapUtil.addGrid
import ru.fivefourtyfive.map.presentation.util.MapUtil.addListener
import ru.fivefourtyfive.map.presentation.util.MapUtil.addMyLocation
import ru.fivefourtyfive.map.presentation.util.MapUtil.addRotationGestureOverlay
import ru.fivefourtyfive.map.presentation.util.MapUtil.addScale
import ru.fivefourtyfive.map.presentation.util.MapUtil.addTilesFrom
import ru.fivefourtyfive.map.presentation.util.MapUtil.clear
import ru.fivefourtyfive.map.presentation.util.MapUtil.init
import ru.fivefourtyfive.map.presentation.util.MapUtil.tileSource
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
import timber.log.Timber
import javax.inject.Inject
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : NavFragment(), EventDispatcher<MapEvent> {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    @Inject
    lateinit var mapView: MapView

    @Inject
    lateinit var myLocation: MyLocationNewOverlay

    private lateinit var placeTitle: TextView

    private lateinit var progress: ProgressBar

    private var places = arrayListOf<PlacePolygon>()

    private var currentSelection: PlacePolygon? = null

    private val folder = FolderOverlay()

    private val labels = arrayListOf<IGeoPoint>()

    private var currentMode = MapMode.SCHEME

    private val listener = DelayedMapListener(object : MapListener {
        override fun onScroll(event: ScrollEvent?): Boolean {
            updatePositionAndRequestLocation()
            return true
        }

        override fun onZoom(event: ZoomEvent?): Boolean {
            updatePositionAndRequestLocation()
            return true
        }
    }, MAP_LISTENER_DELAY)

    private fun updatePositionAndRequestLocation() {
        with(mapView) {
            viewModel.setLastLocation(mapCenter.latitude, mapCenter.longitude)
                .setLastZoom(zoomLevelDouble)
            viewModel.areWikimapiaOverlayEnabled().ifTrue {
                viewModel.getArea(
                    boundingBox.lonWest,
                    boundingBox.latSouth,
                    boundingBox.lonEast,
                    boundingBox.latNorth
                )
            }
        }
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
        viewModel = ViewModelProvider(this, providerFactory).get(MapFragmentViewModel::class.java)
        view.apply {
            mapView.init().addListener(listener)
            findViewById<FrameLayout>(R.id.map_placeholder).addView(mapView)
            progress = findViewById(R.id.progress)
            placeTitle = findViewById(R.id.place_title)
        }
        setMap()
        centerAndZoom()
        subscribeObserver()
    }

    private fun setMap() {
        with(viewModel) {
            progress.visibility = GONE
            mapView.clear()
                .tileSource(getTileSource())
                .addRotationGestureOverlay()
                .addTilesFrom(transportationTileSource, !areWikimapiaOverlayEnabled())
                .addTilesFrom(labelsTileSource, !areWikimapiaOverlayEnabled())
                .addFolder(folder, areWikimapiaOverlayEnabled())
                .addTilesFrom(wikimapiaTileSource, areWikimapiaOverlayEnabled())
                .addGrid(isGridEnabled())
                .addScale(isScaleEnabled())
                .addMyLocation(myLocation)
                .invalidate()
            switchFollowLocation(viewModel.isFollowLocationEnabled())
        }
    }

    private fun switchFollowLocation(enable: Boolean){
        Timber.e("SWITCHING FOLLOWING TO $enable")
//        myLocation.enableAutoStop = enable
        when(enable){
            true -> {myLocation.enableFollowLocation()
//                myLocation.runOnFirstFix{
//                    MainScope().launch {
//                        mapView.controller.animateTo(myLocation.myLocation)
//                        mapView.controller.setZoom(9.5)
//                    }
//                }
            }
            false -> myLocation.disableFollowLocation()
        }
        mapView.invalidate()
    }

    private fun centerAndZoom() {
        val (lat, lon) = viewModel.getLastLocation()
        mapView.controller.apply {
            animateTo(GeoPoint(lat, lon))
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
            folder.items.map {
                with(it as PlacePolygon) {
                    newPlaces.contains(this).ifFalse { itemsToRemove.add(this) }
                }
            }
            folder.items.removeAll(itemsToRemove)
            itemsToRemove.clear()
            newPlaces.map {
                folder.items.contains(it).ifTrue { itemsToRemove.add(it) }
            }
            newPlaces.removeAll(itemsToRemove)
            folder.items.addAll(newPlaces)
            folder.items.map { (it as PlacePolygon).setOnClickListener(PlaceOnClickListener(it)) }
            withContext(Main) { mapView.invalidate() }
        }
    }

    private suspend fun filLabels() {
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
                place.setOnClickListener(PlaceOnClickListener(place))
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
            item(R.id.action_wikimapia_overlays).isChecked = areWikimapiaOverlayEnabled()
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
                setMap()
            }
            R.id.action_map_mode_satellite -> {
                item.isChecked = true
                dispatchEvent(MapEvent.SwitchMapModeEvent(MapMode.SATELLITE))
                setMap()
            }
            R.id.action_wikimapia_overlays -> {
                item.isChecked = !item.isChecked
                dispatchEvent(MapEvent.SwitchWikimapiaOverlayEvent(item.isChecked))
                setMap()
            }
            R.id.action_follow_location -> {
                item.isChecked = !item.isChecked
                dispatchEvent(MapEvent.SwitchFollowLocationEvent(item.isChecked))
                switchFollowLocation(item.isChecked)
            }
            R.id.action_center_selection -> {
                item.isChecked = !item.isChecked
                dispatchEvent(MapEvent.SwitchCenterSelectionEvent(item.isChecked))
                setMap()
            }
            R.id.action_show_scale -> {
                item.isChecked = !item.isChecked
                dispatchEvent(MapEvent.SwitchScaleEvent(item.isChecked))
                setMap()
            }
            R.id.action_show_grid -> {
                item.isChecked = !item.isChecked
                dispatchEvent(MapEvent.SwitchGridEvent(item.isChecked))
                setMap()
            }
            R.id.action_search -> navigate(appR.id.action_mapFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchEvent(event: MapEvent) = viewModel.handleEvent(event)

    override fun onResume() {
        super.onResume()
        myLocation.enableMyLocation()
        setMap()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        myLocation.disableMyLocation()
        mapView.clear().onPause()
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
            place.setHighlighted(!place.highlight)
            mapView?.invalidate()
            return true
        }
    }
}