package ru.fivefourtyfive.wikimapper.domain.entity.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Places
import ru.fivefourtyfive.wikimapper.domain.entity.Place

class AreaDTO(places: Places) : PlacesDTO<PlaceDTO>(places){

    override fun convert(place: Place) = PlaceDTO(place)

}