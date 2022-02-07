package ru.fivefourtyfive.map.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.domain.entity.dto.AreaDTO
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapRepository
import ru.fivefourtyfive.places.util.ifTrue
import ru.fivefourtyfive.places.util.stackTraceToString
import timber.log.Timber
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IMapRepository {

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
        Timber.e("FETCHING BY:  ${latMin}, ${lonMin}, ${latMax}, ${lonMax}")
        kotlin.runCatching {
            localDataSource
//            .getArea(lonMin, latMin, lonMax, latMax, category, count, language)
                .getArea(latMin, lonMin, latMax, lonMax, category, count, language)
                .apply {
                    Timber.e("FOUND PLACES: ${places?.size}")

                    if (places?.isNotEmpty() == true) {
                        emit(AreaDataState.Success(AreaDTO(this)))
//                        emit(AreaDataState.Loading)
                    }
                }
        }.onFailure { Timber.e("ERROR: ${stackTraceToString(it)}") }
//        remoteDataSource.getArea(lonMin, latMin, lonMax, latMax, category, page, count, language)
//            .apply {
//                when (debugInfo) {
//                    null -> emit(AreaDataState.Success(AreaDTO(this)))
//                        .also { withContext(Dispatchers.IO){localDataSource.persistArea(this@apply)} }
//                    else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
//                }
//            }
    }.flowOn(Dispatchers.IO)
}