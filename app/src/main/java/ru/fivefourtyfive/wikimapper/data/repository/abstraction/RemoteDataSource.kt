package ru.fivefourtyfive.wikimapper.data.repository.abstraction

import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.*

interface RemoteDataSource {

    fun getPlace(id: Int, dataBlocks: String? = Parameters.build(
        DataBlock.MAIN,
        DataBlock.PHOTOS,
        DataBlock.COMMENTS
    ))

    fun getPlaces(
        latMin: Float,
        lonMin: Float,
        latMax: Float,
        lonMax: Float,
        category: String? = null,
        count: Int? = 100,
        language: String? = Value.RU
    )

    fun getCategories(
        name: String? = null,
        page: Int? = 1,
        count: Int? = 100,
        language: String? = Value.RU,
    )

    fun search(query: String, latitude: Float, longitude: Float, )
}