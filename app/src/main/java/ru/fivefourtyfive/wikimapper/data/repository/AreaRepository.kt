package ru.fivefourtyfive.wikimapper.data.repository

import ru.fivefourtyfive.wikimapper.domain.interactor.GetArea
import javax.inject.Inject

class AreaRepository @Inject constructor(private val getArea: GetArea) {

    suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        page: Int? = 1
    ) =
        getArea.execute(
            lonMin = lonMin,
            latMin = latMin,
            lonMax = lonMax,
            latMax = latMax,
            page = page
        )
}