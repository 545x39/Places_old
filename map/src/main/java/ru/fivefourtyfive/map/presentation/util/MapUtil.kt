package ru.fivefourtyfive.map.presentation.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.Network
import ru.fivefourtyfive.wikimapper.util.ifTrue
import java.io.File

object MapUtil {

    fun MapView.config(): MapView {

        fun wikimediaTileSource() = XYTileSource(
            "WikimediaNoLabelsTileSource",
            0,
            19,
            256,
            ".png",
            arrayOf(
                Network.WIKIMEDIA_TILES_URL,
                Network.WIKIMEDIA_TILES_URL2,
                Network.WIKIMEDIA_TILES_URL3
            ),
            "© OpenStreetMap contributors",
            TileSourcePolicy(
                2,
                TileSourcePolicy.FLAG_NO_BULK
                        or TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        )

        Configuration.getInstance().apply {
            userAgentValue = BuildConfig.APPLICATION_ID
            osmdroidBasePath = File(context.getExternalFilesDir("osmdroid")!!.absolutePath)
            osmdroidTileCache = File(osmdroidBasePath, "tiles")
        }
        setTileSource(wikimediaTileSource())
        setUseDataConnection(true)
        setMultiTouchControls(true)
        isTilesScaledToDpi = true
        minZoomLevel = 2.0
        maxZoomLevel = 19.0
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        controller.setZoom(10.0)
        isVerticalMapRepetitionEnabled = false
        isHorizontalMapRepetitionEnabled = false
//        invalidate()
        return this
    }

    fun MapView.addFolder(folder: FolderOverlay) = this.apply { overlays.add(folder) }

    fun MapView.addCompass(enable: Boolean = true) =
        this.apply {
            enable.ifTrue {
                CompassOverlay(context, InternalCompassOrientationProvider(context), this).let {
                    it.enableCompass()
                    overlays.add(it)
                }
            }
        }

    fun MapView.addMyLocation(
//        locationOverlay: MyLocationNewOverlay
    ) = this.apply {
        MyLocationNewOverlay(GpsMyLocationProvider(context), this).apply {
            enableMyLocation()
//            enableFollowLocation()
            //enableAutoStop = false
            overlays.add(this)
        }
    }

    fun MapView.addLabels(labels: ArrayList<IGeoPoint>) = this.apply {
        val options = SimpleFastPointOverlayOptions.getDefaultStyle().apply {
            setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(1.0f)
                .setSelectedRadius(100f)
                .setIsClickable(true)
                .setCellSize(10)
                .setTextStyle(Paint().apply {
                    style = Paint.Style.FILL
                    isAntiAlias = true
                    color = Color.GRAY
                    textAlign = Paint.Align.CENTER
                    textSize = 28.0f
                    isFakeBoldText = true
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                })
                .selectedPointStyle = Paint().apply {
                color = 0x00000000//android.R.color.transparent
                strokeWidth = 1.0f
                style = Paint.Style.STROKE
            }
        }
        overlays.add(SimpleFastPointOverlay(SimplePointTheme(labels, true), options))
    }

    fun MapView.addListener(listener: MapListener) = this.apply { addMapListener(listener) }

    fun MapView.addScale(enable: Boolean = true) = this.apply {
        enable.ifTrue { overlays.add(ScaleBarOverlay(this).also { it.setAlignBottom(true) }) }
    }
}