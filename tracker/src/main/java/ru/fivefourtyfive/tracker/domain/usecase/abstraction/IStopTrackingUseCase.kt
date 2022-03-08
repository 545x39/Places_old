package ru.fivefourtyfive.tracker.domain.usecase.abstraction

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

interface IStopTrackingUseCase : IUseCase<Unit> {

    fun init(track: Track): IStopTrackingUseCase

}