package ru.fivefourtyfive.wikimapper.data.datasource.remote

import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource

class FakeRemoteDataSource : RemoteDataSource {

    override suspend fun getPlace(id: Int, dataBlocks: String?) = when (id) {
        0 -> FakeData.getFort()
        1 -> FakeData.getDefenceLine()
        else -> FakeData.getPlaceError()
    }

    override suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String?,
        count: Int?,
        language: String?
    ) = FakeData.getArea()

    override fun getCategories(name: String?, page: Int?, count: Int?, language: String?) {
        //TODO
    }

    override fun search(query: String, latitude: Float, longitude: Float) {
        //TODO
    }
}