package ru.fivefourtyfive.tracker.domain.usecase.abstraction

import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

interface IRecordPointUseCase : IUseCase<Unit> {

    fun init(point: TrackPoint) : IRecordPointUseCase
}