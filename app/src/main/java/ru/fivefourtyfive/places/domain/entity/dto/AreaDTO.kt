package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.domain.entity.Places
import ru.fivefourtyfive.places.domain.entity.Place

class AreaDTO(places: Places) : PlacesDTO<PlaceDTO>(places){

    override fun convert(place: Place) = PlaceDTO(place)

}