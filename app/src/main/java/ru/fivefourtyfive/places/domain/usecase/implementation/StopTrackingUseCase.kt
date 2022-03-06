package ru.fivefourtyfive.places.domain.usecase.implementation

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

class StopTrackingUseCase: IUseCase<Unit> {

    private lateinit var track: Track

    override suspend fun execute() {
        //TODO
    }

    fun init(track: Track) = this.apply { this.track = track }
}