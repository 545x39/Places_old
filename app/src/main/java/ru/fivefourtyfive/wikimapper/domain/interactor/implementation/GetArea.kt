package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.entity.dto.AreaDTO
import javax.inject.Inject

class GetArea @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) {

    suspend fun execute(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = flow {
        emit(AreaDataState.Loading)
        remoteDataSource.getArea(lonMin, latMin, lonMax, latMax, category, page, count, language)
            .apply {
                when (debugInfo) {
                    null -> emit(AreaDataState.Success(AreaDTO(this)))
                        .also { withContext(IO){localDataSource.persistArea(this@apply)} }
                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
                }
            }
    }.flowOn(IO)
}