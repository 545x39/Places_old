package ru.fivefourtyfive.map.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
import ru.fivefourtyfive.map.presentation.dto.PlaceDTO
import ru.fivefourtyfive.map.presentation.dto.PlaceLabel
import ru.fivefourtyfive.map.presentation.util.MAP_LISTENER_DELAY
import ru.fivefourtyfive.map.presentation.util.MapUtil.addCompass
import ru.fivefourtyfive.map.presentation.util.MapUtil.addFolder
import ru.fivefourtyfive.map.presentation.util.MapUtil.addLabels
import ru.fivefourtyfive.map.presentation.util.MapUtil.addListener
import ru.fivefourtyfive.map.presentation.util.MapUtil.addMyLocation
import ru.fivefourtyfive.map.presentation.util.MapUtil.addScale
import ru.fivefourtyfive.map.presentation.util.MapUtil.config
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.presentation.ui.NavFragment
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

    private var places = arrayListOf<PlaceDTO>()

    private var currentSelection: PlaceDTO? = null

    private val folder = FolderOverlay()

    private val labels = arrayListOf<IGeoPoint>()

    private val listener = DelayedMapListener(object : MapListener {
        override fun onScroll(event: ScrollEvent?): Boolean {
//            requestLocation()
            return false
        }

        override fun onZoom(event: ZoomEvent?): Boolean {
//            requestLocation()
            Timber.e("MAP RESIZED. Zoom: [${event?.zoomLevel}]")
            return false
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
        mapView.config().addListener(listener)
        view.findViewById<Button>(R.id.get_area_button).setOnClickListener {
            requestLocation()
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
    }

    private fun onSuccess(newPlaces: ArrayList<PlaceDTO>) {
        //TODO Подобрать более контрастный цвет для контуров объектов.
        //TODO Сделать навигацию в описание объекта по щелчку на названии выбранного объекта.
        //TODO Добавить кнопку центровки на своём положении, добавить ей возможность менять масштаб, аналогично оригинальному приложению.
        //TODO ОТРЕФАКТОРИТЬ!!!!!!!!!!!!11111)
//                        it.places.retainAll()
        MainScope().launch {
            //TODO Удалить то, что вышло за пределы видимой области карты, нарисовать то, что вновь попало в видимую область. То, что уже было, не трогать.
            folder.items.clear()
            labels.clear()
            places.apply {
                clear()
                addAll(newPlaces)
                parallelMap { place ->
                    folder.add(place)
                    //TODO Добавлять название только для достаточно крупных объектов. Перенести в масштабирование
                    labels.add(
                        PlaceLabel(
                            place.id,
                            place.lat,
                            place.lon,
                            place.title
                        )
                    )
                    place.setOnClickListener(PlaceOnClickListener(place))
                }
            }
            mapView.invalidate()
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
        mapView.addFolder(folder)
            .addLabels(labels)
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

    inner class PlaceOnClickListener(private val place: PlaceDTO) : Polygon.OnClickListener {
        override fun onClick(polygon: Polygon?, mapView: MapView?, eventPos: GeoPoint?): Boolean {
            currentSelection?.let { if (it != place) currentSelection?.setHighlighted(false) }
            currentSelection = place
            place.setHighlighted(!place.highlight)
            Toast.makeText(requireContext(), place.title, Toast.LENGTH_SHORT).show()
            mapView?.invalidate()
            Timber.e("BOX WIDTH: ${this@MapFragment.mapView.boundingBox.lonEast - this@MapFragment.mapView.boundingBox.lonWest}, " +
                    " PLACE WIDTH: : ${place.east - place.west}")
            return true
        }
    }
}