package ru.fivefourtyfive.wikimapper.data.repository

import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.repository.IAreaRepository
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.GetArea
import javax.inject.Inject

class AreaRepository @Inject constructor(private val getArea: GetArea): IAreaRepository {

    override suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        page: Int?
    ) =
        getArea.execute(
            lonMin = lonMin,
            latMin = latMin,
            lonMax = lonMax,
            latMax = latMax,
            page = page
        )
}