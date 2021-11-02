package ru.fivefourtyfive.wikimapper.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import javax.inject.Inject

class GetPlace @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun execute(id: Int, dataBlocks: String?) = flow {
        emit(PlaceDetailsDataState.Loading)
        remoteDataSource.getPlace(
            id = id,
            dataBlocks = dataBlocks
        ).apply {
            when (debugInfo) {
                null -> emit(PlaceDetailsDataState.Success(PlaceDescriptionDTO(this)))
                else -> emit(PlaceDetailsDataState.Error(message = debugInfo?.message ?: ""))
            }
        }
    }.flowOn(Dispatchers.IO)
}