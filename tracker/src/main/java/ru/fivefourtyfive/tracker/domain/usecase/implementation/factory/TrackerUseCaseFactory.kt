package ru.fivefourtyfive.tracker.domain.usecase.implementation.factory

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStartLocationUpdatesUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStopLocationUpdatesUseCase
import ru.fivefourtyfive.tracker.domain.usecase.implementation.GetActiveTrackUseCase
import ru.fivefourtyfive.tracker.domain.usecase.implementation.RecordPointUseCase
import ru.fivefourtyfive.tracker.domain.usecase.implementation.StopTrackingUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.factory.ITrackerUseCaseFactory
import javax.inject.Inject

class TrackerUseCaseFactory @Inject constructor(
    private val getActiveTrackUseCase : GetActiveTrackUseCase
)  : ITrackerUseCaseFactory {

    override fun getActiveTrackUseCase() = getActiveTrackUseCase

    override fun recordPointUseCase(point: TrackPoint) = RecordPointUseCase().init(point)

    override fun stopTrackingUseCase(track: Track): IUseCase<Unit>  = StopTrackingUseCase().init(track)

    override fun startLocationUpdatesUseCase(): IStartLocationUpdatesUseCase {

    }

    override fun stopLocationUpdatesUseCase(): IStopLocationUpdatesUseCase {
    }
}