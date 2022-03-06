package ru.fivefourtyfive.places.framework.datasource.implementation.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint

@Dao
abstract class TrackDAO {

    @Insert
    abstract suspend fun insertTrack(track: Track)

    @Insert
    abstract suspend fun insertPath(points: List<TrackPoint>)

    @Insert
    abstract suspend fun insertPoint(point: TrackPoint)

    @Delete
    abstract suspend fun deleteTrack(track: Track)

    @Query("SELECT * FROM tracks WHERE id = :id")
    abstract fun getTrack(id: Int): Track

    @Query("SELECT * FROM track_points WHERE track_id = :trackId")
    abstract fun getTrackPoints(trackId: Int): List<TrackPoint>

    fun getTrackWithPath(id: Int) = getTrack(id).apply { path = getTrackPoints(id) }

}