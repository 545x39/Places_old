package ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database

import androidx.room.RoomDatabase
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database.dao.LocationDAO
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database.dao.PlaceDAO
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database.util.Version.DB_VERSION
import ru.fivefourtyfive.wikimapper.domain.entity.Location
import ru.fivefourtyfive.wikimapper.domain.entity.Place

@androidx.room.Database(
    entities = [Place::class, Location::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun placeDAO(): PlaceDAO

    abstract fun locationDAO(): LocationDAO

}