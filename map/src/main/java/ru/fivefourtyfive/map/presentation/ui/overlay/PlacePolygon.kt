package ru.fivefourtyfive.map.presentation.ui.overlay

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import timber.log.Timber

class PlacePolygon(
    val id: Int,
    name: String,
    val url: String,
    val north: Double,
    val south: Double,
    val east: Double,
    val west: Double,
    val lat: Double,
    val lon: Double,
    polygon: ArrayList<GeoPoint>
) : Polygon() {

    var highlight = false

    init {
        //Forces the loop to close, as the first and the last points are the same.
        polygon.add(polygon[0])
        outlinePaint.color = Color.WHITE
        outlinePaint.strokeWidth = 4.0f
        runCatching {
            points = polygon
        }.onFailure {points = listOf()}
        title = name
    }

    private val highlightColor = Color.argb(80, 49, 137, 135)
    private val transparentColor = Color.argb(0, 255, 255, 255)

    fun setHighlighted(enable: Boolean) {
        fillPaint.color = when (enable) {
            true -> highlightColor
            false -> transparentColor
        }
        highlight = enable
    }

    fun haveToShowLabel(mapView: MapView?): Boolean {
        mapView?.boundingBox?.apply {
            val widthDiff = (east - west) / (lonEast - lonWest)
            val heightDiff = (north - south) / (latNorth - latSouth)
            val isWithinTheBox =
                (east - west) <= (lonEast - lonWest) && (north - south) <= (latNorth - latSouth)
            return isWithinTheBox && (widthDiff >= 0.3 || heightDiff >= 0.3)
        }
        return false
    }

    override fun equals(other: Any?) = when (other) {
        null -> false
        is PlacePolygon -> id == other.id
        else -> false
    }

    override fun hashCode() = id
}