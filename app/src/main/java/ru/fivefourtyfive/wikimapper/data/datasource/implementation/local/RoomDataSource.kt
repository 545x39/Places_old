package ru.fivefourtyfive.wikimapper.data.datasource.implementation.local

import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.LocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.database.Database
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.Places
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val database: Database) : LocalDataSource {

    override suspend fun persistPlace(place: Place) = database.placeDAO().insert(place)

    override suspend fun persistArea(area: Places) {
        area.places?.let {
            database.placeDAO().insertAll(it)
            it.map { place ->
                place.location?.apply {
                    placeId = place.id
                    database.locationDAO().insert(this)
                }
            }
        }
    }

    override suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String?,
        count: Int?,
        language: String?
    ) = database.placeDAO().getPlaces(latMin, lonMin, latMax, lonMax)
        .let { Places(places = it, count = it.size, found = it.size) }

    override suspend fun getPlacesCount() = database.placeDAO().getPlacesCount()

//    override suspend fun getPlace(id: Int): Place {
//        //TODO
//    }
//
//
//    override suspend fun persistSearchResults(places: Places) {
//        //TODO
//    }
//
//    override suspend fun search(
//        query: String,
//        category: String?,
//        count: Int?,
//        language: String?
//    ): Places {
//        //TODO
//    }
}