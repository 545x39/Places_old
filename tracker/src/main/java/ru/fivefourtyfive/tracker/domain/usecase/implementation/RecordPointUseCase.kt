package ru.fivefourtyfive.tracker.domain.usecase.implementation

import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IRecordPointUseCase
import timber.log.Timber
import javax.inject.Inject

class RecordPointUseCase @Inject constructor(private val repository: ITrackRepository) :
    IRecordPointUseCase {

    private lateinit var point: TrackPoint

    override suspend fun execute() {
        //TODO Just logging at this moment.
        Timber.e("NEW TRACK POINT: [${point.x},${point.y}]")
    }

    override fun init(point: TrackPoint) = this.apply { this.point = point }
}