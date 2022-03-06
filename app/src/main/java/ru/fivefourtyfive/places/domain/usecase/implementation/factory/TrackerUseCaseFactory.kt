package ru.fivefourtyfive.places.domain.usecase.implementation.factory

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import ru.fivefourtyfive.places.domain.usecase.abstraction.factory.ITrackerUseCaseFactory
import ru.fivefourtyfive.places.domain.usecase.implementation.GetActiveTrackUseCase
import ru.fivefourtyfive.places.domain.usecase.implementation.RecordPointUseCase
import ru.fivefourtyfive.places.domain.usecase.implementation.StopTrackingUseCase

class TrackerUseCaseFactory  : ITrackerUseCaseFactory{

    override fun getActiveTrackUseCase() = GetActiveTrackUseCase()

    override fun recordPointUseCase(point: TrackPoint) = RecordPointUseCase().init(point)

    override fun stopTrackingUseCase(track: Track): IUseCase<Unit>  = StopTrackingUseCase().init(track)
}