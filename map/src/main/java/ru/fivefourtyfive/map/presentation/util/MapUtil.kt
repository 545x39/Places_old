package ru.fivefourtyfive.map.presentation.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Environment
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.modules.SqlTileWriter
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.MapView.getTileSystem
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme
import ru.fivefourtyfive.map.presentation.util.Zoom.ZOOM_DEFAULT
import ru.fivefourtyfive.map.presentation.util.Zoom.ZOOM_MAX
import ru.fivefourtyfive.map.presentation.util.Zoom.ZOOM_MIN
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.Network.ARCGIS_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMAPIA_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMEDIA_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.ifTrue
import timber.log.Timber
import java.io.File


object MapUtil {

    fun MapView.init() = this.apply {
        Configuration.getInstance().apply {
            userAgentValue = BuildConfig.APPLICATION_ID
            osmdroidBasePath = File("${context.filesDir}")
            osmdroidTileCache = File(osmdroidBasePath, "cache")
            Timber.e("CACHE DIR: " + osmdroidTileCache.absolutePath)
        }
        setUseDataConnection(true)
        setMultiTouchControls(true)
        setScrollableAreaLimitLatitude(
            getTileSystem().maxLatitude,
            getTileSystem().minLatitude,
            0
        )
        setScrollableAreaLimitLongitude(
            getTileSystem().minLongitude,
            getTileSystem().maxLongitude,
            80
        )
        minZoomLevel = ZOOM_MIN
        maxZoomLevel = ZOOM_MAX
        isTilesScaledToDpi = true
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        controller.setZoom(ZOOM_DEFAULT)
        isVerticalMapRepetitionEnabled = false
        isHorizontalMapRepetitionEnabled = false
    }

    fun MapView.setZoomLevels(levels: Pair<Double, Double> = ZOOM_MIN to ZOOM_MAX) = this.apply {
        minZoomLevel = levels.first
        maxZoomLevel = levels.second
    }

    fun MapView.config() = this.apply {
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
        setScrollableAreaLimitLatitude(
            getTileSystem().maxLatitude,
            getTileSystem().minLatitude,
            0
        )
        setScrollableAreaLimitLongitude(
            getTileSystem().minLongitude,
            getTileSystem().maxLongitude,
            80
        )
        minZoomLevel = ZOOM_MIN
        maxZoomLevel = ZOOM_MAX
        isTilesScaledToDpi = true
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        controller.setZoom(ZOOM_DEFAULT)
        isVerticalMapRepetitionEnabled = false
        isHorizontalMapRepetitionEnabled = false
    }

    fun MapView.addWikimapiaTileLayer() = this.apply {
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
        addTilesFrom(wikimapiaTileSource)
    }

    /** Maximum zoom level for this source is 17, in case of greater number it sends gray tiles with "No tile data available" text on them.*/
    fun MapView.addImageryLayer() = this.apply {
        //<editor-fold defaultstate="collapsed" desc="TILE SOURCE">
        val arcGisTileSource = object : OnlineTileSourceBase(
            "ArcGISTileSource",
            0,
            17,
            256,
            ".png",
            ARCGIS_TILE_SERVERS,
            "© OpenStreetMap contributors",
        ) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/World_Imagery/MapServer/tile/${
//                "$baseUrl/ArcGIS/rest/services/Reference/World_Transportation/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }
        //</editor-fold>
        addTilesFrom(arcGisTileSource)
    }

    fun MapView.addTilesFrom(source: OnlineTileSourceBase, add: Boolean = false) = this.apply {
        add.ifTrue {
            with(MapTileProviderBasic(context).also { it.tileSource = source }) {
                overlays.add(TilesOverlay(this, context).apply {
                    loadingBackgroundColor = Color.TRANSPARENT
                })
            }
        }
    }

    fun MapView.tileSource(tileSource: OnlineTileSourceBase) =
        this.apply { setTileSource(tileSource) }

    fun MapView.clear() = this.apply {
        overlays.clear()
    }

    fun MapView.addRotationGestureOverlay() = this.apply {
        overlays.add(RotationGestureOverlay(this).also { it.isEnabled = true })
    }

    fun MapView.addFolder(folder: FolderOverlay, add: Boolean = false) =
        this.apply { add.ifTrue { overlays.add(folder) } }

    fun MapView.addCompass(enable: Boolean = true) =
        this.apply {
            enable.ifTrue {
                CompassOverlay(context, InternalCompassOrientationProvider(context), this).apply {
                    enableCompass()
                    overlays.add(this)
                }
            }
        }

    fun MapView.addMyLocation(overlay: MyLocationNewOverlay) = this.apply {overlays.add(overlay)}

    fun MapView.addLabels(labels: ArrayList<IGeoPoint>, add: Boolean = true) = this.apply {
        add.ifTrue {
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
    }

    fun MapView.addGrid(add: Boolean = false) =
        this.apply { add.ifTrue { overlays.add(LatLonGridlineOverlay2()) } }

    fun MapView.addListener(listener: MapListener) = this.apply { addMapListener(listener) }

    fun MapView.addScale(enable: Boolean = true) = this.apply {
        enable.ifTrue {
            overlays.add(ScaleBarOverlay(this).also {
                it.setAlignBottom(true)
            })
        }
    }

    private fun MapView.clearTileCache() {
        val cache =
            File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "osmdroid" + File.separator + "tiles")
        val filenames = cache.list()
        for (filename in filenames) {
            Timber.e("Cache file $filename")
        }
        val sqlTileWriter = SqlTileWriter()
        val isCleared = SqlTileWriter().purgeCache(tileProvider.tileSource.name())
    }
}