package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IGetPlaceFromNetworkUseCase
import javax.inject.Inject

class GetPlaceFromNetworkUseCase @Inject constructor(private val dataSource: IRemoteDataSource) :
    IGetPlaceFromNetworkUseCase {

    override var id: Int? = null

    override var dataBlocks: String? = null

    override suspend fun execute(): Place? {
        return id?.let{dataSource.getPlace(
            id = it,
            dataBlocks = dataBlocks)}
    }
}