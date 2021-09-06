package ru.fivefourtyfive.map.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.R
import ru.fivefourtyfive.wikimapper.R as appR
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameter.ID
import java.io.File
import kotlin.random.Random

class MapFragment : Fragment() {

    private var locationOverlay: MyLocationNewOverlay? = null

    private var locationProvider: GpsMyLocationProvider? = null

    lateinit var mapView: MapView

    lateinit var splitInstallManager: SplitInstallManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(appR.string.app_name)
        setHasOptionsMenu(true)
        mapView = view.findViewById(R.id.map)
        splitInstallManager = SplitInstallManagerFactory.create(requireContext())
    }

    override fun onResume() {
        super.onResume()
        setMap()
    }

    //<editor-fold defaultstate="collapsed" desc="MAP">
    private fun setMap() {
        val context: Context = mapView.context
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
            setTileSource(TileSourceFactory.MAPNIK)
            setUseDataConnection(true)
            isTilesScaledToDpi = true
            setMultiTouchControls(true)
            minZoomLevel = 2.0
            maxZoomLevel = 20.0
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
            controller.setZoom(10.0)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_map, menu)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> navigateToSettings()
            R.id.action_object_details -> navigateToObjectDetails()
            R.id.action_search -> navigateToSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSearch(){
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_searchFragment,
            null,
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(installMonitor)
    }

    private fun navigateToObjectDetails(){
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_objectDetailsFragment,
            bundleOf(ID to Random.nextInt(1, 50000)),
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(installMonitor)
    }

    private fun navigateToSettings() {
        val installMonitor = DynamicInstallMonitor()
        findNavController().navigate(
            appR.id.action_mapFragment_to_settingsFragment,
            null,
            null,
            DynamicExtras(installMonitor)
        )
        observeInstall(installMonitor)
    }

    private fun observeInstall(installMonitor: DynamicInstallMonitor) {
        /////
        val REQUEST_CODE_INSTALL_CONFIRMATION = 2
        //
        installMonitor.status.observe(viewLifecycleOwner,
            Observer { state ->
                when (state.status()) {
                    SplitInstallSessionStatus.INSTALLED -> {
                        //Модуль установлен, можно дёрнуть навигацию к нему.

                    }
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                        //Большие модули требуют подтверждения установки от пользователя, нужно вызвать специальный диалог (а можно поступить и по-своему)
                        splitInstallManager.startConfirmationDialogForResult(
                            state,// состояние
                            requireActivity(),//активность, в которой будет дёргаться onActivityResult()
                            REQUEST_CODE_INSTALL_CONFIRMATION//код запроса, который свалится в onActivityResult()
                        )
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        //Установка облажалась сама
                    }
                    SplitInstallSessionStatus.CANCELED -> {
                        //Установка отменена пользователем.
                    }
                }
                if (state.hasTerminalStatus()) {
                    installMonitor.status.removeObservers(viewLifecycleOwner) //Отписаться.
                }
            })
    }
}