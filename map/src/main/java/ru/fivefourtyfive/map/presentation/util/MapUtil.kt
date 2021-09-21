package ru.fivefourtyfive.map.presentation.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.Network.ARCGIS_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.GENERAL_HEADQUARTERS_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMAPIA_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMEDIA_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.ifTrue
import java.io.File


object MapUtil {

    fun MapView.config()= this.apply{
    //<editor-fold defaultstate="collapsed" desc="TILE SOURCE">
        val wikimediaTileSource = XYTileSource(
            "WikimediaNoLabelsTileSource",
            0,
            19,
            256,
            ".png",
            WIKIMEDIA_TILE_SERVERS,
            "© OpenStreetMap contributors",
            TileSourcePolicy(
                2,
                TileSourcePolicy.FLAG_NO_BULK
                        or TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        )
        //</editor-fold>
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
    }

    fun MapView.addWikimapiaTileLayer() = this.apply{
        //<editor-fold defaultstate="collapsed" desc="TILE SOURCE">
        val wikimapiaTileSource = object : OnlineTileSourceBase(
            "WikimapiaTileSource",
            0,
            19,
            256,
            ".png",
            WIKIMAPIA_TILE_SERVERS,
            "© OpenStreetMap contributors",
        ) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/?x=${MapTileIndex.getX(pMapTileIndex)}&y=${MapTileIndex.getY(pMapTileIndex)}&zoom=${
                    MapTileIndex.getZoom(pMapTileIndex)
                }&type=hybrid&lng=1"
        }
        //</editor-fold>
        addTileOverlayFrom(wikimapiaTileSource)
    }

    fun MapView.addGeneralHeadquartersTiles() = this.apply{
        //<editor-fold defaultstate="collapsed" desc="TILE SOURCE">
        val generalHeadquartersTileSource = object : OnlineTileSourceBase(
            "GeneralHeadquartersTileSource",
            10,
            13,
            256,
            ".png",
            GENERAL_HEADQUARTERS_TILE_SERVERS,
            "© OpenStreetMap contributors",
        ) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/cgi-bin/tapp/tilecache.py/1.0.0/topomapper_v2/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getX(pMapTileIndex)}/${MapTileIndex.getY(pMapTileIndex)}"
        }
    //</editor-fold>
        addTileOverlayFrom(generalHeadquartersTileSource)
    }

    /** Maximum zoom level for this source is 18, in case of greater number it sends gray tiles with "No tile data available" text on them.*/
    fun MapView.addImageryLayer() = this.apply {
        //<editor-fold defaultstate="collapsed" desc="TILE SOURCE">
        val arcGisTileSource = object : OnlineTileSourceBase(
            "ArcGISTileSource",
            0,
            18,
            256,
            ".png",
            ARCGIS_TILE_SERVERS,
            "© OpenStreetMap contributors",
        ) {
            //http://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/17/38053/76359
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/World_Imagery/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }
        //</editor-fold>
        addTileOverlayFrom(arcGisTileSource)
    }

    private fun MapView.addTileOverlayFrom(source: OnlineTileSourceBase) = this.apply {
        with(MapTileProviderBasic(context).also { it.tileSource = source }) {
            overlays.add(TilesOverlay(this, context).apply {
                loadingBackgroundColor = Color.TRANSPARENT
            })
        }
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

    fun MapView.addGrid() = this.apply { overlays.add(LatLonGridlineOverlay2()) }

    fun MapView.addListener(listener: MapListener) = this.apply { addMapListener(listener) }

    fun MapView.addScale(enable: Boolean = true) = this.apply {
        enable.ifTrue { overlays.add(ScaleBarOverlay(this).also { it.setAlignBottom(true) }) }
    }
}