package ru.fivefourtyfive.map.presentation.dto

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polygon

class PlaceDTO(
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

    init {
        //Forces the loop to close, as the first and the last points are the same.
        polygon.add(polygon[0])
        outlinePaint.color = Color.WHITE
        outlinePaint.strokeWidth = 3.0f
        points = polygon
        title = name
    }

    fun setHighlighted(highlight: Boolean) {
        fillPaint.color = when (highlight) {
            true -> Color.argb(80, 49, 137, 135)
            false -> Color.argb(255, 255, 255, 255)
        }
    }

    override fun equals(other: Any?) = when (other) {
        null -> false
        is PlaceDTO -> id == other.id
        else -> false
    }

    override fun hashCode() = id
}