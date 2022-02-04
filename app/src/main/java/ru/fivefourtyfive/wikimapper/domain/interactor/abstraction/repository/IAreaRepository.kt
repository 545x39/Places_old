package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.repository

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState

interface IAreaRepository {

    suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        page: Int? = 1
    ): Flow<AreaDataState>
}