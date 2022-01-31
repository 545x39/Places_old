package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.IRemoteDataSource
import javax.inject.Inject

class GetCategories @Inject constructor(private val remoteDataSource: IRemoteDataSource) {

    suspend fun execute(name: String? = null, page: Int? = 1) = flow {
        remoteDataSource.getCategories(name, page).apply { emit(this) }
    }.flowOn(Dispatchers.IO)

}
