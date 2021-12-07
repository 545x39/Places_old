package ru.fivefourtyfive.wikimapper.data.datasource.abstraction

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.Places

interface LocalDataSource {

    suspend fun persistPlace(place: Place)

    suspend fun persistArea(area: Places)

    suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String?,
        count: Int?,
        language: String?
    ): Places

    suspend fun getPlacesCount(): Int

//    suspend fun getPlace(id: Int): Place
//
//    suspend fun persistSearchResults(places: Places)
//
//    suspend fun search(query: String,
//                       category: String? = null,
//                       count: Int? = Value.MAX_OBJECTS_PER_PAGE,
//                       language: String? = Value.RU): Places
}