package ru.fivefourtyfive.map.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
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
import ru.fivefourtyfive.map.presentation.dto.PlaceLabel
import ru.fivefourtyfive.map.presentation.dto.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MAP_LISTENER_DELAY
import ru.fivefourtyfive.map.presentation.util.MapUtil.addCompass
import ru.fivefourtyfive.map.presentation.util.MapUtil.addFolder
import ru.fivefourtyfive.map.presentation.util.MapUtil.addGrid
import ru.fivefourtyfive.map.presentation.util.MapUtil.addLabels
import ru.fivefourtyfive.map.presentation.util.MapUtil.addListener
import ru.fivefourtyfive.map.presentation.util.MapUtil.addMyLocation
import ru.fivefourtyfive.map.presentation.util.MapUtil.addScale
import ru.fivefourtyfive.map.presentation.util.MapUtil.addWikimapiaTiles
import ru.fivefourtyfive.map.presentation.util.MapUtil.config
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.NavFragment
import ru.fivefourtyfive.wikimapper.util.ifFalse
import ru.fivefourtyfive.wikimapper.util.ifTrue
import ru.fivefourtyfive.wikimapper.util.parallelMap
import timber.log.Timber
import javax.inject.Inject
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : NavFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    private lateinit var mapView: MapView

    private lateinit var progress: ProgressBar

    private var places = arrayListOf<PlacePolygon>()

    private var currentSelection: PlacePolygon? = null

    private val folder = FolderOverlay()

    private val labels = arrayListOf<IGeoPoint>()

    private val listener = DelayedMapListener(object : MapListener {
        override fun onScroll(event: ScrollEvent?): Boolean {
            requestLocation()
            Timber.e("MAP SCROLLED. Coords: [${mapView.mapCenter.latitude}, ${mapView.mapCenter.longitude}]")
            return true
        }

        override fun onZoom(event: ZoomEvent?): Boolean {
            requestLocation()
            Timber.e("MAP RESIZED. Zoom: [${event?.zoomLevel}]")
            return true
        }
    }, MAP_LISTENER_DELAY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerMapFragmentComponent.factory()
            .create((requireActivity().application as Wikimapper).appComponent)
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
        }
        mapView.config().addWikimapiaTiles().addGrid().addListener(listener)
        view.findViewById<Button>(R.id.get_area_button).setOnClickListener {
            requestLocation()
        }
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
        // TODO СДЕЛАТЬ DEBOUNCE запросов!!!
        //TODO Сделать навигацию в описание объекта по щелчку на названии выбранного объекта.
        //TODO Добавить кнопку центровки на своём положении, добавить ей возможность менять масштаб, аналогично оригинальному приложению.
        //TODO ОТРЕФАКТОРИТЬ!!!!!!!!!!!!11111)
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

    private fun requestLocation() {
        mapView.boundingBox.apply { viewModel.getArea(lonWest, latSouth, lonEast, latNorth) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_map, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> navigate(appR.id.action_mapFragment_to_searchFragment)
            R.id.action_object_details -> navigate(
                appR.id.action_mapFragment_to_placeDetailsFragment,
                bundleOf(ID to currentSelection?.id)
            )
            R.id.action_search -> navigate(appR.id.action_mapFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mapView
            .addWikimapiaTiles()
            .addFolder(folder)
//            .addLabels(labels)
            .addGrid()
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
            place.setHighlighted(!place.highlight)
            Toast.makeText(requireContext(), place.title, Toast.LENGTH_SHORT).show()
            mapView?.invalidate()
            return true
        }
    }
}