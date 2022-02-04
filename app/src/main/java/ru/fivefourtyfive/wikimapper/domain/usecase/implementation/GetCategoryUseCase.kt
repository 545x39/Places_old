package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.framework.datasource.remote.util.Value.RU
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val remoteDataSource: IRemoteDataSource)  {

    suspend fun execute(id: Int, language: String? = RU) = flow {
            remoteDataSource.getCategory(id, language).apply { emit(this) }
        }.flowOn(Dispatchers.IO)
}