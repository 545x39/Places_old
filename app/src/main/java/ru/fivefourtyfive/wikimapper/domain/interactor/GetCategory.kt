package ru.fivefourtyfive.wikimapper.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.RU
import javax.inject.Inject

class GetCategory @Inject constructor(private val remoteDataSource: RemoteDataSource)  {

    suspend fun execute(id: Int, language: String? = RU) = flow {
            remoteDataSource.getCategory(id, language).apply { emit(this) }
        }.flowOn(Dispatchers.IO)
}