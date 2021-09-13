package ru.fivefourtyfive.map.presentation.viewmodel

import org.osmdroid.util.GeoPoint

data class PlaceDTO(
    val id: Int,
    val name: String,
    val url: String,
    val north: Double,
    val south: Double,
    val east: Double,
    val west: Double,
    val lat: Double,
    val lon: Double,
    val polygon: List<GeoPoint>
){
    override fun equals(other: Any?) = when(other){
        null -> false
        is PlaceDTO -> id == other.id
        else -> false
    }
}