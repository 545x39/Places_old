package ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory

import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromCacheUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromNetworkUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase

interface IUseCaseFactory {

    fun getPlaceFromNetworkUseCase(): IGetPlaceFromNetworkUseCase

    fun getPlaceFromCacheUseCase(): IGetPlaceFromCacheUseCase

    fun getPersistPlaceCacheUseCase(): IPersistPlaceUseCase
}