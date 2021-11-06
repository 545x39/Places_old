package ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.database.dao

import androidx.room.*
import ru.fivefourtyfive.wikimapper.domain.entity.Location
import ru.fivefourtyfive.wikimapper.domain.entity.Place

@Dao
abstract class PlaceDAO {

    @Transaction
    open suspend fun insert(place: Place) {
        place.apply {
            insertPlace(this)
            location?.let {
                it.placeId = id
                insertLocation(it)
            }
        }
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(places: List<Place>)

    //    suspend fun insertAll(places: List<Place>) = places.map { insert(it) }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlace(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLocation(location: Location)

    @Query("SELECT COUNT(1) FROM places")
    abstract suspend fun getPlacesCount(): Int

}