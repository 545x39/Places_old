package ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromCacheUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromNetworkUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetPlaceFromNetworkUseCase
import javax.inject.Inject

class UseCaseFactory @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IUseCaseFactory {

    override fun getPlaceFromNetworkUseCase(
        id: Int,
        dataBlocks: String?
    ): IGetPlaceFromNetworkUseCase =
        GetPlaceFromNetworkUseCase(remoteDataSource).withId(id).withDataBlocks(dataBlocks)

    override fun getPlaceFromCacheUseCase(id: Int): IGetPlaceFromCacheUseCase {
        TODO("Not yet implemented")
    }

    override fun persistPlaceUseCase(place: Place): IPersistPlaceUseCase {
        TODO("Not yet implemented")
    }
}