package ru.fivefourtyfive.wikimapper.data.repository

import ru.fivefourtyfive.wikimapper.domain.interactor.GetArea
import javax.inject.Inject

class AreaRepository @Inject constructor(private val getArea: GetArea) {

    suspend fun getArea(latMin: Double, lonMin: Double, latMax: Double, lonMax: Double) =
        getArea.execute(latMin, lonMin, latMax, lonMax)

}