package ru.fivefourtyfive.wikimapper.data.repository

import ru.fivefourtyfive.wikimapper.domain.interactor.GetArea
import javax.inject.Inject

class AreaRepository @Inject constructor(private val getArea: GetArea) {

    suspend fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double,
        page: Int? = 1
    ) =
        getArea.execute(
            latMin = latMin,
            lonMin = lonMin,
            latMax = latMax,
            lonMax = lonMax,
            page = page
        )

}