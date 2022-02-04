package ru.fivefourtyfive.wikimapper.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.entity.dto.AreaDTO
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IAreaRepository
import javax.inject.Inject

class AreaRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IAreaRepository {

    override suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = flow {
        emit(AreaDataState.Loading)
        remoteDataSource.getArea(lonMin, latMin, lonMax, latMax, category, page, count, language)
            .apply {
                when (debugInfo) {
                    null -> emit(AreaDataState.Success(AreaDTO(this)))
                        .also { withContext(Dispatchers.IO){localDataSource.persistArea(this@apply)} }
                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
                }
            }
    }.flowOn(Dispatchers.IO)
}