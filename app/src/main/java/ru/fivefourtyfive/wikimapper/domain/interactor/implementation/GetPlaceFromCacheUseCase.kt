package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromCacheUseCase

class GetPlaceFromCacheUseCase (private val dataSource: ILocalDataSource) :
    IGetPlaceFromCacheUseCase {

    override var id: Int? = null

    override suspend fun execute(): Place? = id?.let { return@execute dataSource.getPlace(it) }
}