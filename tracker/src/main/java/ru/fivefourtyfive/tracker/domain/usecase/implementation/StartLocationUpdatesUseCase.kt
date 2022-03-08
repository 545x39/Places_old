package ru.fivefourtyfive.tracker.domain.usecase.implementation

import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.IStartLocationUpdatesUseCase
import javax.inject.Inject

class StartLocationUpdatesUseCase @Inject constructor(private val repository: ITrackRepository) :
    IStartLocationUpdatesUseCase {

    override suspend fun execute() = repository.startLocationUpdates()
}