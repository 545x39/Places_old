package ru.fivefourtyfive.tracker.domain.usecase.implementation

import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IGetActiveTrackUseCase
import javax.inject.Inject

class GetActiveTrackUseCase @Inject constructor(private val repository: ITrackRepository) :
    IGetActiveTrackUseCase {

    override suspend fun execute() = repository.getActiveTrack()
}