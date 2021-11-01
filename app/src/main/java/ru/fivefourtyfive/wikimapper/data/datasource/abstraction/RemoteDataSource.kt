package ru.fivefourtyfive.wikimapper.data.datasource.abstraction

import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.DataBlock
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameters
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.wikimapper.domain.entity.Area
import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface RemoteDataSource {

    suspend fun getPlace(
        id: Int, dataBlocks: String? = Parameters.add(
            DataBlock.MAIN,
            DataBlock.PHOTOS,
            DataBlock.COMMENTS,
            DataBlock.GEOMETRY,
            DataBlock.LOCATION
        )
    ): Place

    suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ): Area

    fun getCategories(
        name: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU,
    )

    fun search(query: String, latitude: Float, longitude: Float)
}