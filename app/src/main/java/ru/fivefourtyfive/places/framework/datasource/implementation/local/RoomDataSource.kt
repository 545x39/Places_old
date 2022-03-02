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
        .let { Places(places = it, count = it.size, found = it.size) }

    override suspend fun getPlacesCount() = database.placeDAO().getPlacesCount()

    override suspend fun getPlace(id: Int) = database.placeDAO().getPlace(id)

}