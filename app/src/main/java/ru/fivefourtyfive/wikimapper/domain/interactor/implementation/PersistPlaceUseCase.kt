package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.ILocalDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase
import javax.inject.Inject

class PersistPlaceUseCase @Inject constructor(private val localDataSource: ILocalDataSource) :
    IPersistPlaceUseCase {

    override var place: Place? = null

    override suspend fun execute() {
        place?.let { localDataSource.persistPlace(it) }
    }
}