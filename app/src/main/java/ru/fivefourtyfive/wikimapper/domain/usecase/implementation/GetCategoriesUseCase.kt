package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val remoteDataSource: IRemoteDataSource) {

    suspend fun execute(name: String? = null, page: Int? = 1) = flow {
        remoteDataSource.getCategories(name, page).apply { emit(this) }
    }.flowOn(Dispatchers.IO)

}
