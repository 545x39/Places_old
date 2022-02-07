package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.domain.entity.Place
import ru.fivefourtyfive.places.domain.entity.Places

class SearchResultsDTO(places: Places): PlacesDTO<FoundPlaceDTO>(places)
{
    override fun convert(place: Place) = FoundPlaceDTO(place)
}