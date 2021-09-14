package ru.fivefourtyfive.map.presentation.ui

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
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
import org.osmdroid.config.Configuration
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.map.di.DaggerMapFragmentComponent
import ru.fivefourtyfive.map.presentation.dto.PlaceDTO
import ru.fivefourtyfive.map.presentation.util.InstallObserver.observeInstall
import ru.fivefourtyfive.map.presentation.util.parallelMap
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import ru.fivefourtyfive.wikimapper.presentation.ui.MainActivity
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMEDIA_TILES_URL
import java.io.File
import javax.inject.Inject
import kotlin.random.Random
import ru.fivefourtyfive.wikimapper.R as appR

class MapFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var viewModel: MapFragmentViewModel

    private var locationOverlay: MyLocationNewOverlay? = null

    private var locationProvider: GpsMyLocationProvider? = null

    private lateinit var mapView: MapView

    private lateinit var progress: ProgressBar

    private var currentPlaces = listOf<PlaceDTO>()

    private var currentSelection: PlaceDTO? = null

    lateinit var splitInstallManager: SplitInstallManager

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
        view.findViewById<Button>(R.id.get_area_button).setOnClickListener {
            mapView.boundingBox.apply { viewModel.getArea(lonWest, latSouth, lonEast, latNorth) }
            viewModel.liveData.observe(viewLifecycleOwner, {
                with(it) {
                    progress.visibility = progressVisibility
                    when (this) {
                        is MapViewState.Success -> {
                            //TODO Удалить то, что вышло за пределы видимой области карты,
                            // нарисовать то, что вновь попало в видимую область. То, что уже было, не трогать.
                            //TODO перерисовывать своё местонахождение поверх всех оверлеев.
                            //TODO Перерисовывать компас поверх всех оверлеев.
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
                            }
                        }
                        is MapViewState.Error -> (requireActivity() as MainActivity)
                            .showSnackBar(message)
                        is MapViewState.Loading -> {/* Do nothing*/
                        }
                    }
                }
            })
        }
        splitInstallManager = SplitInstallManagerFactory.create(requireContext())
    }

    private val selectedTextStyle = Paint().apply {
        color = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        strokeWidth = 1.0f
        style = Paint.Style.STROKE
    }

    private val textStyle = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = ContextCompat.getColor(
            requireContext(),
            0//R.color.
        )
        textAlign = Paint.Align.LEFT;
        textSize = 30.0f;
        isFakeBoldText = true;
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
    }


    // set some visual options for the overlay
    // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
    private val overlayOptions = SimpleFastPointOverlayOptions.getDefaultStyle()
        .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
        .setRadius(1.0f)
        .setSelectedRadius(100f)
        .setIsClickable(true)
        .setCellSize(1)
        .setTextStyle(textStyle)
        .setSelectedPointStyle(selectedTextStyle)

    inner class Listener(private val place: PlaceDTO) : Polygon.OnClickListener {
        override fun onClick(polygon: Polygon?, mapView: MapView?, eventPos: GeoPoint?): Boolean {
            currentSelection?.let { if (it != place) currentSelection?.setHighlighted(false) }
            currentSelection = place
            place.setHighlighted(!place.highlight)
            //TODO Show place title.
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
            return true
        }
    }

    override fun onResume() {
        super.onResume()
        setMap(requireContext())
        setMapListener()
    }

    private val wikimediaTileSource = XYTileSource(
        "OsmNoLabels",
        0,
        19,
        256,
        ".png",
        arrayOf(WIKIMEDIA_TILES_URL),
        "© OpenStreetMap contributors",
        TileSourcePolicy(
            2,
            TileSourcePolicy.FLAG_NO_BULK
                    or TileSourcePolicy.FLAG_NO_PREVENTIVE
                    or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                    or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
        )
    )

    //<editor-fold defaultstate="collapsed" desc="MAP">
    private fun setMap(context: Context) {
    //val context: Context = mapView.context

        fun setCompassOverlay() {
            CompassOverlay(context, InternalCompassOrientationProvider(context), mapView).apply {
                enableCompass()
                mapView.overlays.add(this)
            }
        }

        val configuration = Configuration.getInstance()
        configuration.userAgentValue = BuildConfig.APPLICATION_ID
        configuration.osmdroidBasePath =
            File(requireActivity().applicationContext.getExternalFilesDir("osmdroid")!!.absolutePath)
        configuration.osmdroidTileCache = File(configuration.osmdroidBasePath, "tiles")
        mapView.apply {
//            setTileSource(TileSourceFactory.MAPNIK)
            setTileSource(wikimediaTileSource)
            setUseDataConnection(true)
            isTilesScaledToDpi = true
            setMultiTouchControls(true)
            minZoomLevel = 2.0
            maxZoomLevel = 19.0
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
            controller.setZoom(10.0)
            isVerticalMapRepetitionEnabled = false
            isHorizontalMapRepetitionEnabled = false
//        zoomController.setZoomInEnabled(false)
//        zoomController.setZoomOutEnabled(false)
        }
        setCompassOverlay()
        ///
        locationProvider = GpsMyLocationProvider(context)
        locationOverlay = MyLocationNewOverlay(locationProvider, mapView)
        val directedLocationOverlay = DirectedLocationOverlay(context)
        ////
        mapView.overlays.add(locationOverlay)
        mapView.overlays.add(directedLocationOverlay)
        locationOverlay?.apply {
            enableMyLocation()
            enableFollowLocation()
//        enableAutoStop = false
        }
        mapView.invalidate()
    }
//</editor-fold>

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