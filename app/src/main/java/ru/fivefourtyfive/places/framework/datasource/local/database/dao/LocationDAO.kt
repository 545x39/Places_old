package ru.fivefourtyfive.places.framework.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import ru.fivefourtyfive.places.domain.entity.Location

@Dao
interface LocationDAO {

    @Insert(onConflict = REPLACE)
    fun insert(location: Location)

}