package ru.fivefourtyfive.wikimapper.data.datasource.implementation.local

import androidx.room.RoomDatabase
import ru.fivefourtyfive.wikimapper.domain.entity.Place

@androidx.room.Database(
    entities = [Place::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    //TODO Add DAO here.
}