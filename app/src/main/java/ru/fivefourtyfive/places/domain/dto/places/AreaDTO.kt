package ru.fivefourtyfive.places.domain.dto.places

import ru.fivefourtyfive.places.domain.entity.places.Places
import ru.fivefourtyfive.places.domain.entity.places.Place

class AreaDTO(places: Places) : PlacesDTO<PlaceDTO>(places){

    override fun convert(place: Place) = PlaceDTO(place)

}