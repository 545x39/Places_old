package ru.fivefourtyfive.places.domain.usecase.abstraction.factory

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.entity.tracks.TrackPoint
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

interface ITrackerUseCaseFactory {

    fun getActiveTrackUseCase(): IUseCase<Track>

    fun recordPointUseCase(point: TrackPoint): IUseCase<Unit>

    fun stopTrackingUseCase(track : Track): IUseCase<Unit>
}