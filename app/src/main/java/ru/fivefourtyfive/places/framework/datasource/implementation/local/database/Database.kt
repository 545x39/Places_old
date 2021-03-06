package ru.fivefourtyfive.places.framework.datasource.implementation.local.database

import androidx.room.RoomDatabase
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao.LocationDAO
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao.PlaceDAO
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.util.Version.DB_VERSION
import ru.fivefourtyfive.places.domain.entity.places.Location
import ru.fivefourtyfive.places.domain.entity.places.Place
import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao.TrackDAO

@androidx.room.Database(
    entities = [Place::class, Location::class, Track::class, TrackPoint::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun placeDAO(): PlaceDAO

    abstract fun locationDAO(): LocationDAO

    abstract fun trackDAO(): TrackDAO

}