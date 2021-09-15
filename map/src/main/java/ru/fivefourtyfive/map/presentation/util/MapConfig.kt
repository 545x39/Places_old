package ru.fivefourtyfive.map.presentation.util

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.Network
import java.io.File

object MapConfig {

    fun MapView.config() {
        Configuration.getInstance().apply {
            userAgentValue = BuildConfig.APPLICATION_ID
            osmdroidBasePath = File(context.getExternalFilesDir("osmdroid")!!.absolutePath)
            osmdroidTileCache = File(osmdroidBasePath, "tiles")
        }
        setTileSource(wikimediaTileSource)
        setUseDataConnection(true)
        setMultiTouchControls(true)
        isTilesScaledToDpi = true
        minZoomLevel = 2.0
        maxZoomLevel = 19.0
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        controller.setZoom(10.0)
        isVerticalMapRepetitionEnabled = false
        isHorizontalMapRepetitionEnabled = false
        invalidate()
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