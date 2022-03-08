package ru.fivefourtyfive.tracker.domain.usecase.implementation

import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStopLocationUpdatesUseCase
import javax.inject.Inject

class StopLocationUpdatesUseCase @Inject constructor(private val repository: ITrackRepository) :
    IStopLocationUpdatesUseCase {

    override suspend fun execute() = repository.stopLocationUpdates()
}