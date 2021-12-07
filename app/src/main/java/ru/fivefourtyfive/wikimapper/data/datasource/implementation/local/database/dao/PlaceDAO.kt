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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlace(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLocation(location: Location)

    @Query("SELECT COUNT(1) FROM places")
    abstract suspend fun getPlacesCount(): Int

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM places p LEFT JOIN locations l ON p.id = l.place_id WHERE ((north >= :south AND east >= :west AND north <= :north and east <= :east) OR ((south >= :south AND west >= :west AND south <= :north and west <= :east))) AND (north - south >= (:north - :south)/:limiter AND east - west >=(:east - :west) /:limiter);")
    abstract suspend fun getPlaces(
        north: Double,
        east: Double,
        south: Double,
        west: Double,
        limiter: Int = 10
    ): List<Place>

}