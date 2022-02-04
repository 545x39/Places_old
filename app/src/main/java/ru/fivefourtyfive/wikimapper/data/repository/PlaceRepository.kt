package ru.fivefourtyfive.wikimapper.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.repository.IPlaceRepository
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory.IUseCaseFactory
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val factory: IUseCaseFactory): IPlaceRepository {

    override suspend fun getPlace(id: Int, dataBlocks: String?): Flow<PlaceDetailsDataState> = flow {
        emit(PlaceDetailsDataState.Loading)
        factory.getPlaceFromCacheUseCase(id).execute()
            ?.let {
                emit(PlaceDetailsDataState.Success(PlaceDescriptionDTO(it)))
                emit(PlaceDetailsDataState.Loading)
            }
        emit(when (val place: Place? =
            factory.getPlaceFromNetworkUseCase(id, dataBlocks).execute()) {
            null -> PlaceDetailsDataState.Error()
            else -> PlaceDetailsDataState.Success(PlaceDescriptionDTO(place))
                .also {
                    CoroutineScope(IO).launch {
                        factory.persistPlaceUseCase(place).execute()
                    }
                }
        })
    }.flowOn(IO)
}