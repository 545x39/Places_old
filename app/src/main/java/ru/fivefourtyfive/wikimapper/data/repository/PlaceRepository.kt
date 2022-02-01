package ru.fivefourtyfive.wikimapper.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetPlaceFromCacheUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetPlaceFromNetworkUseCase
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val getPlaceFromCacheUseCase: GetPlaceFromCacheUseCase,
    private val getPlaceFromNetworkUseCase: GetPlaceFromNetworkUseCase,
    private val persistPlaceUseCase: IPersistPlaceUseCase
) {

    suspend fun getPlace(id: Int, dataBlocks: String? = null) = flow {
        emit(PlaceDetailsDataState.Loading)
        getPlaceFromCacheUseCase.withId(id).execute()
            ?.let { emit(PlaceDetailsDataState.Success(PlaceDescriptionDTO(it))) }
        emit(PlaceDetailsDataState.Loading)
        when (val place: Place? = runCatching {
            getPlaceFromNetworkUseCase
                .withId(id)
                .withDataBlocks(dataBlocks)
                .execute()
        }.getOrNull()) {
            null -> PlaceDetailsDataState.Error()
            else -> PlaceDetailsDataState.Success(PlaceDescriptionDTO(place))
                .also {
                    CoroutineScope(IO).launch {
                        persistPlaceUseCase.withPlace(place).execute()
                    }
                }
        }

    }.flowOn(IO)
}