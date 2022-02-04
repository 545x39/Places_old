package ru.fivefourtyfive.wikimapper.domain.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState

interface IGetPlaceUseCase : IUseCase<Flow<PlaceDetailsDataState>> {

    var id: Int

    var dataBlocks: String?


    fun init(id: Int = 1, dataBlocks: String? = null) {
        this.id = id
        this.dataBlocks = dataBlocks
    }
}