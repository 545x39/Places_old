package ru.fivefourtyfive.map.presentation.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Environment
import org.osmdroid.api.IGeoPoint
import org.osmdroid.events.MapListener
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.modules.SqlTileWriter
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
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
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.wikimapper.domain.dto.MapPlaceDTO
import ru.fivefourtyfive.wikimapper.util.ifTrue
import java.io.File


object MapUtil {

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

    fun MapView.addListener(listener: MapListener) = this.apply { addMapListener(listener) }

    private fun MapView.clearTileCache() {
        val cache =
            File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "osmdroid" + File.separator + "tiles")
        val filenames = cache.list()
        SqlTileWriter().purgeCache(tileProvider.tileSource.name())
    }

}

fun MapPlaceDTO.toPlacePolygon() = PlacePolygon(
    id = id,
    name = title,
    url = url,
    north = north,
    south = south,
    east = east,
    west = west,
    lat = lat,
    lon = lon,
    polygon = arrayListOf<GeoPoint>().apply {
        polygon.map { add(GeoPoint(it.y, it.x)) }
    }
)