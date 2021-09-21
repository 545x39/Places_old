package ru.fivefourtyfive.map.presentation.ui.overlay

import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint

class PlaceLabel(val id: Int, latitude: Double, longitude:  Double, title: String) : LabelledGeoPoint(latitude, longitude, title) {

    override fun equals(other: Any?) = when (other) {
        null -> false
        is PlaceLabel -> id == other.id
        else -> false
    }

    override fun hashCode() = id
}