package ru.fivefourtyfive.tracker.data.repository.implementation

import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.tracker.data.datasource.abstraction.ILocationDataSource
import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val locationDataSource: ILocationDataSource,
    private val localDataSource: ILocalDataSource
) : ITrackRepository {

    override fun startLocationUpdates() = locationDataSource.startLocationUpdates()


    override fun stopLocationUpdates() = locationDataSource.stopLocationUpdates()

    override fun getActiveTrack(): Track {
        //TODO Stub. To be removed.
        return Track(1)
    }

    override fun recordTrackPoint(point: TrackPoint) {
        //TODO Stub. To be removed.
    }

}