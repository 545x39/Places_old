package ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao

import androidx.room.*
import ru.fivefourtyfive.places.domain.entity.Location
import ru.fivefourtyfive.places.domain.entity.Place

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlace(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLocation(location: Location)

    @Query("SELECT COUNT(1) FROM places")
    abstract suspend fun getPlacesCount(): Int

    //(north - south) * (east - west) >= ((60.04390944606621 - 59.9752860293248)*(29.768305507565344 - 29.69188190732538))/1000
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM places p LEFT JOIN locations l ON p.id = l.place_id WHERE ((north >= :south AND east >= :west AND north <= :north and east <= :east) OR ((south >= :south AND west >= :west AND south <= :north and west <= :east))) AND (north - south >= (:north - :south)/:limiter AND east - west >=(:east - :west) /:limiter);")
    abstract suspend fun getPlaces(
        north: Double,
        east: Double,
        south: Double,
        west: Double,
        limiter: Int = 25
    ): List<Place>

    @Query("SELECT * FROM places WHERE id = :id")
    abstract suspend fun getPlace(id: Int): Place?
}