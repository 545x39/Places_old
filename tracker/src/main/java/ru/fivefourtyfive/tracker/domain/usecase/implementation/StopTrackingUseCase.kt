package ru.fivefourtyfive.tracker.domain.usecase.implementation

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStopTrackingUseCase

class StopTrackingUseCase: IStopTrackingUseCase {

    private lateinit var track: Track

    override suspend fun execute() {
        //TODO
    }

    override fun init(track: Track) = this.apply { this.track = track }
}