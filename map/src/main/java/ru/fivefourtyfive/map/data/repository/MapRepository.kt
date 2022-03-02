package ru.fivefourtyfive.map.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapRepository
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.domain.entity.dto.AreaDTO
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IMapRepository {

    override suspend fun getArea(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = flow {
        emit(AreaDataState.Loading)
        kotlin.runCatching {
            localDataSource.getArea(north, west, south, east, category, count, language)
                .apply {
                    if (places?.isNotEmpty() == true) {
                        emit(AreaDataState.Success(AreaDTO(this)))
                    }
                }
        }

        remoteDataSource.getArea(north, west, south, east, category, page, count, language)
            .apply {
                when (debugInfo) {
                    null -> emit(AreaDataState.Success(AreaDTO(this)))
                        .also { withContext(Dispatchers.IO){localDataSource.persistArea(this@apply)} }
                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
                }
            }
    }.flowOn(Dispatchers.IO)
}