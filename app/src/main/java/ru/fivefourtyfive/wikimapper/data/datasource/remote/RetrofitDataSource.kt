package ru.fivefourtyfive.wikimapper.data.datasource.remote

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Parameters
import ru.fivefourtyfive.wikimapper.data.repository.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.AreaDTO
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import timber.log.Timber
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val api: Api) : RemoteDataSource {

    override suspend fun getPlace(id: Int, dataBlocks: String?) = flow {
        emit(PlaceDetailsDataState.Loading)
        api.getPlace(
            id = id,
            dataBlocks = dataBlocks
        ).apply {
            when (debugInfo) {
                null -> emit(PlaceDetailsDataState.Success(PlaceDescriptionDTO(this)))
                else -> emit(PlaceDetailsDataState.Error(message = debugInfo?.message ?: ""))
            }
        }
    }.flowOn(IO)

    override suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        category: String?,
        count: Int?,
        language: String?
    ) = flow {
        emit(AreaDataState.Loading)
        api.getArea(
            boundingBox = Parameters.add(latMin, lonMin, latMax, lonMax),
            category = category,
            count = count,
            language = language
        ).apply {
            when (debugInfo) {
                null -> emit(AreaDataState.Success(AreaDTO(this)))
                else -> emit(AreaDataState.Error(message = debugInfo?.message ?: ""))
            }
        }
    }.flowOn(IO)

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