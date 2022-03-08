package ru.fivefourtyfive.tracker.domain.repository.abstraction

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint

interface ITrackRepository {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    fun getActiveTrack(): Track

    fun recordTrackPoint(point: TrackPoint)

}