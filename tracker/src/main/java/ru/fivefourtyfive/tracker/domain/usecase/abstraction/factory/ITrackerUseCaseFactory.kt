package ru.fivefourtyfive.tracker.domain.usecase.abstraction.factory

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStartLocationUpdatesUseCase
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStopLocationUpdatesUseCase

interface ITrackerUseCaseFactory {

    fun getActiveTrackUseCase(): IUseCase<Track>

    fun recordPointUseCase(point: TrackPoint): IUseCase<Unit>

    fun stopTrackingUseCase(track : Track): IUseCase<Unit>

    fun startLocationUpdatesUseCase(): IStartLocationUpdatesUseCase

    fun stopLocationUpdatesUseCase(): IStopLocationUpdatesUseCase
}