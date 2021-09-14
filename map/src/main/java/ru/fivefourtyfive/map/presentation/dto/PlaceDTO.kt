package ru.fivefourtyfive.map.presentation.dto

import android.graphics.Color
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polygon

class PlaceDTO (
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
//        setFillColor(Color.argb(80, 49,137,135))
        polygon.add(polygon[0])//Forces the loop to close, as the first and the last points are the same.
        strokeColor = Color.WHITE
        strokeWidth = 3.0f
        points = polygon
        title = name
    }

    override fun equals(other: Any?) = when (other) {
        null -> false
        is PlaceDTO -> id == other.id
        else -> false
    }

    override fun hashCode() = id
}