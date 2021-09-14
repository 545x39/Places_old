package ru.fivefourtyfive.map.presentation.util

import android.content.Context
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.Network
import java.io.File

object MapConfig {

    fun config(context: Context, mapView: MapView){

        fun setCompassOverlay() {
            CompassOverlay(context, InternalCompassOrientationProvider(context), mapView).apply {
                enableCompass()
                mapView.overlays.add(this)
            }
        }
        val locationOverlay: MyLocationNewOverlay?
        val locationProvider: GpsMyLocationProvider?
        val configuration = Configuration.getInstance()
        configuration.userAgentValue = BuildConfig.APPLICATION_ID
        configuration.osmdroidBasePath =
            File(context.applicationContext.getExternalFilesDir("osmdroid")!!.absolutePath)
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
        locationOverlay.apply {
            enableMyLocation()
            enableFollowLocation()
//        enableAutoStop = false
        }
        mapView.invalidate()
    }

    private val wikimediaTileSource = XYTileSource(
        "OsmNoLabels",
        0,
        19,
        256,
        ".png",
        arrayOf(Network.WIKIMEDIA_TILES_URL),
        "© OpenStreetMap contributors",
        TileSourcePolicy(
            2,
            TileSourcePolicy.FLAG_NO_BULK
                    or TileSourcePolicy.FLAG_NO_PREVENTIVE
                    or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                    or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
        )
    )
}