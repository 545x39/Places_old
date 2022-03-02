package ru.fivefourtyfive.places.framework.datasource.implementation.remote

import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Parameters
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val api: Api) : IRemoteDataSource {

    override suspend fun getPlace(id: Int, dataBlocks: String?) = api.getPlace(
        id = id,
        dataBlocks = dataBlocks
    )

    override suspend fun getArea(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = api.getArea(
        boundingBox = Parameters.add(east, south, west, north),
        category = category,
        page = page,
        count = count,
        language = language
    )

    override suspend fun getCategory(id: Int, language: String?) =
        api.getCategory(id = id, language = language)

    override suspend fun getCategories(name: String?, page: Int?, count: Int?, language: String?) =
        api.getCategories(name = name, page = page, count = count, language = language)


    override suspend fun search(
        query: String,
        latitude: Float,
        longitude: Float,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = api.search(
        query = query,
        latitude = latitude,
        longitude = longitude,
        category = category,
        page = page,
        count = count,
        language = language
    )
}