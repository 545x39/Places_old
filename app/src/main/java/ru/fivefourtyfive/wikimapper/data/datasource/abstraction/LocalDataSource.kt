package ru.fivefourtyfive.wikimapper.data.datasource.abstraction

import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.entity.Places
import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface LocalDataSource {

    suspend fun persistPlace(place: Place): Boolean

    suspend fun getPlace(id: Int): Place

    suspend fun getArea(latMin: Double,
                lonMin: Double,
                latMax: Double,
                lonMax: Double,
                category: String?,
                count: Int?,
                language: String?): Places

    suspend fun persistSearchResults(places: Places)

    suspend fun search(query: String,
                       category: String? = null,
                       count: Int? = Value.MAX_OBJECTS_PER_PAGE,
                       language: String? = Value.RU): Places
}