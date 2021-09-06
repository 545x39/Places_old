package ru.fivefourtyfive.wikimapper.data.datasource.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameters
import ru.fivefourtyfive.wikimapper.data.repository.abstraction.RemoteDataSource
import timber.log.Timber
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val api: Api) : RemoteDataSource {

    override fun getPlace(id: Int, dataBlocks: String?) {
        api.getObject(
            id = id,
            dataBlocks = dataBlocks
        )
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

    override fun getPlaces(
        latMin: Float,
        lonMin: Float,
        latMax: Float,
        lonMax: Float,
        category: String?,
        count: Int?,
        language: String?
    ) {
        api.getObjects(boundingBox = Parameters.build(latMin, lonMin, latMax, lonMax))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    Timber.e("RESPONSE OK!!!")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Timber.e("RESPONSE FAILED!!!")
                }
            })
    }

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

    override fun search(query: String, latitude: Float, longitude: Float) {
        api.search(query = query, latitude = latitude, longitude = longitude)
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
}