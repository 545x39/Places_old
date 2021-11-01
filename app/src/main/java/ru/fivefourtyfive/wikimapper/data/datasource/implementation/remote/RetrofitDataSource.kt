package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameters
import timber.log.Timber
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val api: Api) : RemoteDataSource {

    override suspend fun getPlace(id: Int, dataBlocks: String?) = api.getPlace(
        id = id,
        dataBlocks = dataBlocks
    )

    override suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ) = api.getArea(
        boundingBox = Parameters.add(lonMin, latMin, lonMax, latMax),
        category = category,
        page = page,
        count = count,
        language = language
    )

    override fun getCategories(name: String?, page: Int?, count: Int?, language: String?) {
        api.getCategories(name = name, page = page, count = count, language = language)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Timber.e("RESPONSE OK!!!")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Timber.e("RESPONSE FAILED!!!")
                }
            })
    }

    override fun search(
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