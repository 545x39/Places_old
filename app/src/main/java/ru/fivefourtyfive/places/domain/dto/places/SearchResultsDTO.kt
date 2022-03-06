package ru.fivefourtyfive.places.domain.dto.places

import ru.fivefourtyfive.places.domain.entity.places.Place
import ru.fivefourtyfive.places.domain.entity.places.Places

class SearchResultsDTO(places: Places): PlacesDTO<FoundPlaceDTO>(places)
{
    override fun convert(place: Place) = FoundPlaceDTO(place)
}