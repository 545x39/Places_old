package ru.fivefourtyfive.places.framework.datasource.implementation.remote

import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource

class FakeRemoteDataSource : IRemoteDataSource {

    override suspend fun getPlace(id: Int, dataBlocks: String?) = when (id) {
        0 -> FakeData.getFort()
        1 -> FakeData.getDefenceLine()
        else -> FakeData.getPlaceError()
    }

    override suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = FakeData.getArea(
        lonMin,
        latMin,
        lonMax,
        latMax,
        category,
        page,
        count,
        language
    )

    override suspend fun getCategory(id: Int, language: String?) = FakeData.getCategory()

    override suspend fun getCategories(name: String?, page: Int?, count: Int?, language: String?) =
        FakeData.getCategories()

    override suspend fun search(
        query: String,
        latitude: Float,
        longitude: Float,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = FakeData.getArea(
        0.0,
        0.0,
        0.0,
        0.0,
        category,
        page,
        count,
        language
    )

}