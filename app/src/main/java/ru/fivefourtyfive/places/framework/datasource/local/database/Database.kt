package ru.fivefourtyfive.places.framework.datasource.local.database

import androidx.room.RoomDatabase
import ru.fivefourtyfive.places.framework.datasource.local.database.dao.LocationDAO
import ru.fivefourtyfive.places.framework.datasource.local.database.dao.PlaceDAO
import ru.fivefourtyfive.places.framework.datasource.local.database.util.Version.DB_VERSION
import ru.fivefourtyfive.places.domain.entity.Location
import ru.fivefourtyfive.places.domain.entity.Place

@androidx.room.Database(
    entities = [Place::class, Location::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun placeDAO(): PlaceDAO

    abstract fun locationDAO(): LocationDAO

}