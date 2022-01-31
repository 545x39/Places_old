package ru.fivefourtyfive.wikimapper.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetPlaceFromCacheUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetPlaceFromNetworkUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.PersistPlaceUseCase
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val getPlaceFromCache: GetPlaceFromCacheUseCase,
    private val getPlaceFromNetwork: GetPlaceFromNetworkUseCase,
    private val persistPlaceUseCase: PersistPlaceUseCase
) {

    suspend fun getPlace(id: Int, dataBlocks: String? = null) = flow<PlaceDetailsDataState> {
        emit(PlaceDetailsDataState.Loading)
        emit(
            getPlaceFromCache.Builder()
                .id(id)
                .build()
                .execute()
        )
        emit(PlaceDetailsDataState.Loading)
        emit(getPlaceFromNetwork.Builder()
            .id(id)
            .dataBlocks(dataBlocks)
            .build()
            .execute()
            .also {
                CoroutineScope(IO).launch { persistPlaceUseCase.Builder().place(it).build().execute()
                }
            })
    }.flowOn(IO)
}