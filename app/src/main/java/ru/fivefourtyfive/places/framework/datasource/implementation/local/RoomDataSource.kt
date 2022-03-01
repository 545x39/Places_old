package ru.fivefourtyfive.places.framework.datasource.implementation.local

import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.Database
import ru.fivefourtyfive.places.domain.entity.Place
import ru.fivefourtyfive.places.domain.entity.Places
import timber.log.Timber
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val database: Database) : ILocalDataSource {

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
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String?,
        count: Int?,
        language: String?
    ) = database.placeDAO().getPlaces(north, west, south, east)
        .let { Places(places = it, count = it.size, found = it.size) }.also {
        Timber.e("SELECT * FROM places p LEFT JOIN locations l ON p.id = l.place_id WHERE ((north >= :south AND east >= :west AND north <= :north and east <= :east) OR ((south >= :south AND west >= :west AND south <= :north and west <= :east))) AND (north - south >= (:north - :south)/:limiter AND east - west >=(:east - :west) /:limiter);")
        Timber.e("SELECT * FROM places p LEFT JOIN locations l ON p.id = l.place_id WHERE ((north >= $south AND east >= $west AND north <= $north and east <= $east) OR ((south >= $south AND west >= $west AND south <= $north and west <= $east))) AND (north - south >= ($north - $south)/25 AND east - west >=($east - $west) /25);")
        Timber.e("south: $south, east: $east, north: $north, west: $west") }

    override suspend fun getPlacesCount() = database.placeDAO().getPlacesCount()

    override suspend fun getPlace(id: Int) = database.placeDAO().getPlace(id)
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