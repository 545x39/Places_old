package ru.fivefourtyfive.map.presentation.ui.overlay

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polygon

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
        outlinePaint.color = Color.GRAY
        outlinePaint.strokeWidth = 3.0f
        points = polygon
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

    override fun equals(other: Any?) = when (other) {
        null -> false
        is PlacePolygon -> id == other.id
        else -> false
    }

    override fun hashCode() = id
}