package ru.fivefourtyfive.wikimapper.data.repository

import ru.fivefourtyfive.wikimapper.data.repository.abstraction.RemoteDataSource
import javax.inject.Inject

class AreaRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getArea(latMin: Double, lonMin: Double, latMax: Double, lonMax: Double) =
        remoteDataSource.getArea(latMin, lonMin, latMax, lonMax)

}