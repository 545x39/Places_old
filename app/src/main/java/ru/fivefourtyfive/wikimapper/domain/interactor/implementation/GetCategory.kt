package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.RU
import javax.inject.Inject

class GetCategory @Inject constructor(private val remoteDataSource: IRemoteDataSource)  {

    suspend fun execute(id: Int, language: String? = RU) = flow {
            remoteDataSource.getCategory(id, language).apply { emit(this) }
        }.flowOn(Dispatchers.IO)
}