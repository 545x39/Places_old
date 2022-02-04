package ru.fivefourtyfive.wikimapper.domain.entity.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Place

open class PlaceDTO(place: Place) {
    var debugInfo   = place.debugInfo
    val id          = place.id
    val url         = place.url ?: ""
    val title       = place.name ?: ""
    val polygon     = place.polygon ?: listOf()
    val north       = place.location?.north ?: 0.0
    val south       = place.location?.south ?: 0.0
    val east        = place.location?.east ?: 0.0
    val west        = place.location?.west ?: 0.0
    val lat         = place.location?.lat ?: 0.0
    val lon         = place.location?.lon ?: 0.0

}