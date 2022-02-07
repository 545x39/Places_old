package ru.fivefourtyfive.objectdetails.domain.repository.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.objectdetails.domain.datastate.PlaceDetailsDataState

interface IPlaceDetailsRepository {

    suspend fun getPlace(id: Int, dataBlocks: String? = null): Flow<PlaceDetailsDataState>
}