package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.Places

class SearchResultsDTO(places: Places): PlacesDTO<FoundPlaceDTO>(places)
{
    override fun convert(place: Place) = FoundPlaceDTO(place)
}