package ru.fivefourtyfive.wikimapper.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import javax.inject.Inject

class GetCategories @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun execute(name: String? = null, page: Int? = 1) = flow {
        remoteDataSource.getCategories(name, page).apply { emit(this) }
    }.flowOn(Dispatchers.IO)

}
