package ru.fivefourtyfive.places.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value.RU
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val remoteDataSource: IRemoteDataSource)  {

    suspend fun execute(id: Int, language: String? = RU) = flow {
            remoteDataSource.getCategory(id, language).apply { emit(this) }
        }.flowOn(Dispatchers.IO)
}