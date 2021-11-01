package ru.fivefourtyfive.wikimapper.domain.interactor

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.dto.AreaDTO
import javax.inject.Inject

class GetArea @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun execute(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String? = null,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = flow {
        emit(AreaDataState.Loading)
        remoteDataSource.getArea(latMin, lonMin, latMax, lonMax, category, count, language)
            .apply {
                when (debugInfo) {
                    null -> emit(AreaDataState.Success(AreaDTO(this)))
                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
                }
            }
    }.flowOn(IO)
}