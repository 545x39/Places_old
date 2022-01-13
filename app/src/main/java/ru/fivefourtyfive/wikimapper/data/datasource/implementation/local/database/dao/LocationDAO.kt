package ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import ru.fivefourtyfive.wikimapper.domain.entity.Location

@Dao
interface LocationDAO {

    @Insert(onConflict = REPLACE)
    fun insert(location: Location)

}