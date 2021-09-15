package ru.fivefourtyfive.map.presentation.ui

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.osmdroid.api.IGeoPoint
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.dto.PlaceDTO
import ru.fivefourtyfive.map.presentation.util.InstallObserver.observeInstall
import ru.fivefourtyfive.map.presentation.util.MapConfig.config
import ru.fivefourtyfive.map.presentation.util.parallelMap
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMEDIA_TILES_URL
import javax.inject.Inject
import kotlin.random.Random
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    private lateinit var locationOverlay: MyLocationNewOverlay

    private lateinit var mapView: MapView

    private lateinit var progress: ProgressBar

    private var currentPlaces = listOf<PlaceDTO>()

    private var currentSelection: PlaceDTO? = null

    lateinit var splitInstallManager: SplitInstallManager

    private val selectedTextStyle = Paint()

    private val textStyle = Paint()

//    // set some visual options for the overlay
//    // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
    private val overlayOptions = SimpleFastPointOverlayOptions.getDefaultStyle()

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
        mapView = view.findViewById(R.id.map)
        progress = view.findViewById(R.id.progress)
        ////
        selectedTextStyle.apply {
            color = ContextCompat.getColor(requireContext(), android.R.color.transparent)
            strokeWidth = 1.0f
            style = Paint.Style.STROKE
        }
        ////
        textStyle.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = 28.0f;
            isFakeBoldText = true;
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        }
        setOverlayOptions()
        ////
        view.findViewById<Button>(R.id.get_area_button).setOnClickListener {
            mapView.boundingBox.apply { viewModel.getArea(lonWest, latSouth, lonEast, latNorth) }
            viewModel.liveData.observe(viewLifecycleOwner, {
                with(it) {
                    progress.visibility = progressVisibility
                    when (this) {
                        is MapViewState.Success -> {
                            //TODO Удалить то, что вышло за пределы видимой области карты, нарисовать то, что вновь попало в видимую область. То, что уже было, не трогать.
                            //TODO Сделать навигацию в описание объекта по щелчку на названии выбранного объекта.
                            //TODO Выводить названия объектов (не кликабельные) при масштабировании и перемещении карты в зависимости от соотношения размера объекта к масштабу карты.
                            //TODO Подобрать более контрастный цвет для контуров объектов.
                            //TODO Решить проблему с медленной заливкой цветом выбранного объекта (посмотреть, будет ли быстре еработать изменение цвета контура).
                            //TODO Организовать объекты в FolderOverlay, а названия - в FastOverlay.
                            //TODO Добавить кнопку центровки на своём моложении, добавить ей возможность менять масштаб, аналогично оригинальному приложению.
//                        it.places.retainAll()
                            MainScope().launch {
                                currentPlaces.parallelMap { place ->
                                    mapView.overlayManager.remove(place)
                                }
                                currentPlaces = places
                                currentPlaces.parallelMap { place ->
                                    mapView.overlayManager.add(place)
                                    place.setOnClickListener(Listener(place))
                                }
                                resetCompassOverlay()
                                resetLocationOverlay()
                            }
                        }
                        is MapViewState.Error -> (requireActivity() as MainActivity)
                            .showSnackBar(message)
                        is MapViewState.Loading -> {
                            /* Do nothing*/
                        }
                    }
                }
            })
        }
        splitInstallManager = SplitInstallManagerFactory.create(requireContext())
    }

    inner class Listener(private val place: PlaceDTO) : Polygon.OnClickListener {
        override fun onClick(polygon: Polygon?, mapView: MapView?, eventPos: GeoPoint?): Boolean {
            currentSelection?.let { if (it != place) currentSelection?.setHighlighted(false) }
            currentSelection = place
            place.setHighlighted(!place.highlight)
            Toast.makeText(requireContext(), place.title, Toast.LENGTH_SHORT).show()
//            Marker(mapView).apply {
//                icon = null
//                title = place.title
//                position = GeoPoint(place.lat, place.lon)
//                textLabelForegroundColor = 0
//                mapView?.overlays?.add(this)
//                Timber.e("TITLE WAS: [${place.title}]")
//            }
//            val theme = SimplePointTheme()
//            SimpleFastPointOverlay()
//            mapView?.overlays?.add(SimpleFastPointOverlay)(LabelledGeoPoint(place.lat, place.lon, place.title)))

            // wrap them in a theme
            val points = arrayListOf<IGeoPoint>()
            points.add(LabelledGeoPoint(place.lat, place.lon, place.title))
            val simplePointTheme = SimplePointTheme(points, true)
            mapView?.overlays?.add(SimpleFastPointOverlay(simplePointTheme, overlayOptions))
            return true
        }
    }

    private fun setOverlayOptions(){
        overlayOptions.setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
            .setRadius(1.0f)
            .setSelectedRadius(100f)
            .setIsClickable(true)
            .setCellSize(10)
            .setTextStyle(textStyle)
            .selectedPointStyle = selectedTextStyle

    }

    override fun onResume() {
        super.onResume()
        mapView.config()
        resetCompassOverlay()
        resetLocationOverlay()
        setMapListener()
    }

    private lateinit var compassOverlay: CompassOverlay

    private fun resetLocationOverlay() {
        if (!::locationOverlay.isInitialized) {
            locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
                .apply {
                    enableMyLocation()
                    enableFollowLocation()
//                enableAutoStop = false
                }
        }
        reset(locationOverlay)
    }

    private fun resetCompassOverlay() {
        if (!::compassOverlay.isInitialized) {
            compassOverlay = CompassOverlay(
                context,
                InternalCompassOrientationProvider(context),
                mapView
            ).apply { enableCompass() }
        }
        reset(compassOverlay)
    }

    private fun reset(overlay: Overlay) {
        mapView.overlays.apply {
            remove(overlay)
            add(overlay)
        }
    }

    private fun setMapListener() {
        mapView.addMapListener(DelayedMapListener(object : MapListener {
            override fun onScroll(event: ScrollEvent?): Boolean {
//                Timber.e("MAP SCROLLED. BBOX: " + mapView.boundingBox)
                return false
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
//                Timber.e("MAP RESIZED. BBOX: " + mapView.boundingBox)
                return false
            }
        }, 200))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_map, menu)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> navigateToSettings()
            R.id.action_object_details -> navigateToObjectDetails()
            R.id.action_search -> navigateToSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSearch() {
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_searchFragment,
            null,
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(viewLifecycleOwner, requireActivity(), splitInstallManager, installMonitor)
    }

    private fun navigateToObjectDetails() {
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_placeDetailsFragment,
            bundleOf(ID to Random.nextInt(1, 540)),
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(viewLifecycleOwner, requireActivity(), splitInstallManager, installMonitor)
    }

    private fun navigateToSettings() {
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_settingsFragment,
            null,
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(viewLifecycleOwner, requireActivity(), splitInstallManager, installMonitor)
    }
}