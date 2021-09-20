package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Location

class LocationDTO(location: Location?) {

    val lat: Double = location?.lat ?: 0.0

    val lon: Double = location?.lon ?: 0.0

    val place: String = location?.let { "${it.place}, ${it.country}" } ?: ""
}