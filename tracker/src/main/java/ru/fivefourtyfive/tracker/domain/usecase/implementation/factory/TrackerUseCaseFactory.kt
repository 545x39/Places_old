package ru.fivefourtyfive.tracker.domain.usecase.implementation.factory

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStartLocationUpdatesUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStopLocationUpdatesUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.factory.ITrackerUseCaseFactory
import ru.fivefourtyfive.tracker.domain.usecase.implementation.*
import javax.inject.Inject

class TrackerUseCaseFactory
@Inject constructor(private val repository: ITrackRepository)  : ITrackerUseCaseFactory {

    override fun getActiveTrackUseCase() = GetActiveTrackUseCase(repository)

    override fun recordPointUseCase(point: TrackPoint) = RecordPointUseCase(repository).init(point)

    override fun stopTrackingUseCase(track: Track): IUseCase<Unit>  = StopTrackingUseCase().init(track)

    override fun startLocationUpdatesUseCase() = StartLocationUpdatesUseCase(repository)

    override fun stopLocationUpdatesUseCase() = StopLocationUpdatesUseCase(repository)
}