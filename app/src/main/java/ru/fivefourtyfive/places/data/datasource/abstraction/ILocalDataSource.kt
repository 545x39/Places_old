package ru.fivefourtyfive.places.data.datasource.abstraction

import ru.fivefourtyfive.places.domain.entity.Place
import ru.fivefourtyfive.places.domain.entity.Places

interface ILocalDataSource {

    suspend fun persistPlace(place: Place)

    suspend fun persistArea(area: Places)

    //south, east, north, west
    suspend fun getArea(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String?,
        count: Int?,
        language: String?
    ): Places

    suspend fun getPlacesCount(): Int

    suspend fun getPlace(id: Int): Place?
//
//    suspend fun persistSearchResults(places: Places)
//
//    suspend fun search(query: String,
//                       category: String? = null,
//                       count: Int? = Value.MAX_OBJECTS_PER_PAGE,
//                       language: String? = Value.RU): Places
}