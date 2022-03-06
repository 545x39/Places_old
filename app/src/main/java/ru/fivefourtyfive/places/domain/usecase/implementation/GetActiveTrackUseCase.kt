package ru.fivefourtyfive.places.domain.usecase.implementation

import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

class GetActiveTrackUseCase: IUseCase<Track> {
    override suspend fun execute(): Track {
        //TODO
        return Track(1)
    }
}