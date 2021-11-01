package ru.fivefourtyfive.wikimapper.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.dto.AreaDTO
import javax.inject.Inject

class Search @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun execute(
        query: String,
        latitude: Float,
        longitude: Float,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = flow {
        emit(AreaDataState.Loading)
        remoteDataSource.search(query, latitude, longitude, category, page, count, language)
            .apply {
                //TODO Сделать DataState, закончить с этим кодом.
//                when (debugInfo) {
//                    null -> emit(AreaDataState.Success(AreaDTO(this)))
//                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
//                }
            }
    }.flowOn(Dispatchers.IO)
}