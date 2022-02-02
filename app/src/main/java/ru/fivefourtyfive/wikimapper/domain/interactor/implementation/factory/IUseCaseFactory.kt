package ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromCacheUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromNetworkUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase

interface IUseCaseFactory {

    fun getPlaceFromNetworkUseCase(id: Int, dataBlocks: String?): IGetPlaceFromNetworkUseCase

    fun getPlaceFromCacheUseCase(id: Int): IGetPlaceFromCacheUseCase

    fun persistPlaceUseCase(place: Place): IPersistPlaceUseCase
}