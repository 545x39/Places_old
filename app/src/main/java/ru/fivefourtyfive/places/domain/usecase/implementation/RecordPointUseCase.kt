package ru.fivefourtyfive.places.domain.usecase.implementation

import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import timber.log.Timber

class RecordPointUseCase : IUseCase<Unit> {

    private lateinit var point: TrackPoint

    override suspend fun execute() {
        //TODO Just logging at this moment.
        Timber.e("NEW TRACK POINT: [${point.x},${point.y}]")
    }

    fun init(point: TrackPoint) = this.apply { this.point = point }
}