package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.framework.datasource.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.datastate.SearchResultDataState
import ru.fivefourtyfive.wikimapper.domain.entity.dto.SearchResultsDTO
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