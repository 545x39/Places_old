package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.repository

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState

interface IPlaceRepository {

    suspend fun getPlace(id: Int, dataBlocks: String? = null): Flow<PlaceDetailsDataState>
}