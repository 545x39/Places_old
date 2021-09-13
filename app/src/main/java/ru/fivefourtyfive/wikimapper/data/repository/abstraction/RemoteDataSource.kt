package ru.fivefourtyfive.wikimapper.data.repository.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.DataBlock
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.DataBlocks
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDataState

interface RemoteDataSource {

    suspend fun getPlace(
        id: Int, dataBlocks: String? = DataBlocks.add(
            DataBlock.MAIN,
            DataBlock.PHOTOS,
            DataBlock.COMMENTS,
            DataBlock.GEOMETRY,
            DataBlock.LOCATION
        )
    ): Flow<PlaceDataState>

    suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String? = null,
        count: Int? = 100,
        language: String? = Value.RU
    ): Flow<AreaDataState>

    fun getCategories(
        name: String? = null,
        page: Int? = 1,
        count: Int? = 100,
        language: String? = Value.RU,
    )

    fun search(query: String, latitude: Float, longitude: Float)
}