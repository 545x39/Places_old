package ru.fivefourtyfive.places.domain.repository.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.places.domain.datastate.PlaceDetailsDataState

interface IPlaceRepository {

    suspend fun getPlace(id: Int, dataBlocks: String? = null): Flow<PlaceDetailsDataState>
}