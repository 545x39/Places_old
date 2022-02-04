package ru.fivefourtyfive.wikimapper.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.dto.PlaceDescriptionDTO
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IPlaceRepository
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDatasource: IRemoteDataSource
) : IPlaceRepository {

    override suspend fun getPlace(id: Int, dataBlocks: String?): Flow<PlaceDetailsDataState> =
        flow {
            emit(PlaceDetailsDataState.Loading)
            localDataSource.getPlace(id)
            ?.let {
                emit(PlaceDetailsDataState.Success(PlaceDescriptionDTO(it)))
                emit(PlaceDetailsDataState.Loading)
            }
        emit(when (val place: Place? =
            remoteDatasource.getPlace(id, dataBlocks)) {
            null -> PlaceDetailsDataState.Error()
            else -> PlaceDetailsDataState.Success(PlaceDescriptionDTO(place))
                .also {
                    CoroutineScope(IO).launch {localDataSource.persistPlace(place)}
                }
        })
        }.flowOn(IO)
}