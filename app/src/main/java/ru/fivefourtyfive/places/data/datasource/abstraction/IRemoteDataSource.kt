package ru.fivefourtyfive.places.data.datasource.abstraction

import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.DataBlock
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Parameters
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value
import ru.fivefourtyfive.places.domain.entity.places.Categories
import ru.fivefourtyfive.places.domain.entity.places.Category
import ru.fivefourtyfive.places.domain.entity.places.Places
import ru.fivefourtyfive.places.domain.entity.places.Place

interface IRemoteDataSource {

    suspend fun getPlace(
        id: Int, dataBlocks: String? = Parameters.add(
            DataBlock.MAIN,
            DataBlock.PHOTOS,
            DataBlock.COMMENTS,
            DataBlock.GEOMETRY,
            DataBlock.LOCATION
        )
    ): Place?

    suspend fun getArea(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ): Places

    suspend fun getCategory(id: Int, language: String? = Value.RU): Category

    suspend fun getCategories(
        name: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU,
    ): Categories

    suspend fun search(query: String,
               latitude: Float,
               longitude: Float,
               category: String? = null,
               page: Int? = 1,
               count: Int? = Value.MAX_OBJECTS_PER_PAGE,
               language: String? = Value.RU): Places
}