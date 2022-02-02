package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.ILocalDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromCacheUseCase
import javax.inject.Inject

class GetPlaceFromCacheUseCase (private val dataSource: ILocalDataSource) :
    IGetPlaceFromCacheUseCase {

    override var id: Int? = null

    override suspend fun execute(): Place? = id?.let { return@execute dataSource.getPlace(it) }
}