package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IUseCase
import javax.inject.Inject

class GetPlaceFromNetworkUseCase @Inject constructor(private val dataSource: IRemoteDataSource) :
    IUseCase<PlaceDetailsDataState> {

    private var id: Int = 0
    private var dataBlocks: String? = null

    override suspend fun execute(): PlaceDetailsDataState {
        dataSource.getPlace(
            id = id,
            dataBlocks = dataBlocks
        ).apply {
            return when (debugInfo) {
                null -> PlaceDetailsDataState.Success(PlaceDescriptionDTO(this))
                else -> PlaceDetailsDataState.Error(message = debugInfo?.message ?: "")
            }
        }
    }

    inner class Builder {

        fun id(id: Int) = this.apply { this@GetPlaceFromNetworkUseCase.id = id }

        fun dataBlocks(dataBlocks: String?) =
            this.apply { this@GetPlaceFromNetworkUseCase.dataBlocks = dataBlocks }

        fun build() = this@GetPlaceFromNetworkUseCase
    }
}