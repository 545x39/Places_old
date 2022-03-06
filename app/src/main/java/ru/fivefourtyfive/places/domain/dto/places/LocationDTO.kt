package ru.fivefourtyfive.places.domain.dto.places

import ru.fivefourtyfive.places.domain.entity.places.Location

class LocationDTO(location: Location?) {

    val lat: Double = location?.lat ?: 0.0

    val lon: Double = location?.lon ?: 0.0

    val place: String = location?.let { "${it.place}, ${it.country}" } ?: ""
}