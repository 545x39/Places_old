package ru.fivefourtyfive.places.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.places.domain.datastate.SearchResultDataState
import ru.fivefourtyfive.places.domain.dto.places.SearchResultsDTO
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val remoteDataSource: IRemoteDataSource) {

    suspend fun execute(
        query: String,
        latitude: Float,
        longitude: Float,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = flow {
        emit(SearchResultDataState.Loading)
        remoteDataSource.search(query, latitude, longitude, category, page, count, language)
            .apply {
                when (debugInfo) {
                    null -> emit(SearchResultDataState.Success(SearchResultsDTO(this)))
                    else -> emit(SearchResultDataState.Error(message = debugInfo?.message ?: ""))
                }
            }
    }.flowOn(Dispatchers.IO)
}