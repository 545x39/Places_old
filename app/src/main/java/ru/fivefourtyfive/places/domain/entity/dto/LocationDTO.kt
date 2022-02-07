package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.domain.entity.Location

class LocationDTO(location: Location?) {

    val lat: Double = location?.lat ?: 0.0

    val lon: Double = location?.lon ?: 0.0

    val place: String = location?.let { "${it.place}, ${it.country}" } ?: ""
}