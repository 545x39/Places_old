package ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao

import androidx.room.*
import ru.fivefourtyfive.places.domain.entity.places.Location
import ru.fivefourtyfive.places.domain.entity.places.Place

@Dao
abstract class PlaceDAO {

    @Transaction
    open suspend fun insert(place: Place?) {
        place?.apply {
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

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT p.id AS id, p.object_type AS object_type, p.language_id AS language_id, CASE WHEN p.name IS NULL THEN p.title ELSE p.name END AS name, p.polygon AS polygon, p.is_deleted AS is_deleted, p.is_building AS is_building, p.is_region AS is_region, l.lon AS lon, l.lat AS lat, l.north AS north, l.south AS south, l.east AS east, l.west AS west FROM places p LEFT JOIN locations l ON p.id = l.place_id WHERE ((l.north >= :south AND l.east >= :west AND l.north <= :north AND l.east <= :east) OR ((l.south >= :south AND l.west >= :west AND l.south <= :north and l.west <= :east))) AND (l.north - l.south >= (:north - :south)/:limiter AND l.east - l.west >=(:east - :west) /:limiter);")
    abstract suspend fun getPlaces(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        limiter: Int = 25
    ): List<Place>

    @Query("SELECT * FROM places WHERE id = :id")
    abstract suspend fun getPlace(id: Int): Place?
}