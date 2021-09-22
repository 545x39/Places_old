package ru.fivefourtyfive.map.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
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
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.ui.overlay.PlaceLabel
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MAP_LISTENER_DELAY
import ru.fivefourtyfive.map.presentation.util.MapUtil.addCompass
import ru.fivefourtyfive.map.presentation.util.MapUtil.addFolder
import ru.fivefourtyfive.map.presentation.util.MapUtil.addImageryLayer
import ru.fivefourtyfive.map.presentation.util.MapUtil.addListener
import ru.fivefourtyfive.map.presentation.util.MapUtil.addMyLocation
import ru.fivefourtyfive.map.presentation.util.MapUtil.addScale
import ru.fivefourtyfive.map.presentation.util.MapUtil.addWikimapiaTileLayer
import ru.fivefourtyfive.map.presentation.util.MapUtil.config
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.Places
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.NavFragment
import ru.fivefourtyfive.wikimapper.util.ifFalse
import ru.fivefourtyfive.wikimapper.util.ifTrue
import ru.fivefourtyfive.wikimapper.util.parallelMap
import javax.inject.Inject
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : NavFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    private lateinit var mapView: MapView

    private lateinit var placeTitle: TextView

    private lateinit var progress: ProgressBar

    private var places = arrayListOf<PlacePolygon>()

    private var currentSelection: PlacePolygon? = null

    private val folder = FolderOverlay()

    private val labels = arrayListOf<IGeoPoint>()

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
                .getArea(
                    boundingBox.lonWest,
                    boundingBox.latSouth,
                    boundingBox.lonEast,
                    boundingBox.latNorth
                )
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
            mapView = findViewById(R.id.map)
            progress = findViewById(R.id.progress)
            placeTitle = findViewById(R.id.place_title)
        }
        mapView.config().addListener(listener)
        viewModel.getLastLocation().apply {
            mapView.controller.animateTo(GeoPoint(first, second))
        }
        mapView.controller.setZoom(viewModel.getLastZoom())
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            with(it) {
                progress.visibility = progressVisibility
                when (this) {
                    is MapViewState.Success -> onSuccess(places)
                    is MapViewState.Error -> onError(message)
                    is MapViewState.Loading -> {
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
            labels.clear()
            folder.items.apply {
                parallelMap { place ->
                    (place as PlacePolygon).hasToShowLabel().ifTrue {
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
            withContext(Main) { mapView.invalidate() }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> navigate(appR.id.action_mapFragment_to_searchFragment)
            R.id.action_search -> navigate(appR.id.action_mapFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        /** Overlay addition order matters. */
        mapView.addImageryLayer()
            //.addGeneralHeadquartersTiles()
            .addFolder(folder)
            .addWikimapiaTileLayer()
            //.addLabels(labels)
            //.addGrid()
            .addCompass()
            .addMyLocation()
            .addScale()
            .invalidate()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.overlays.clear()
        mapView.onPause()
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