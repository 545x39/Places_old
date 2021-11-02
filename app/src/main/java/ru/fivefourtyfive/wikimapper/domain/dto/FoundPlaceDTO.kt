package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Place

class FoundPlaceDTO(place: Place) : PlaceDTO(place) {

    val distance = place.distance
}