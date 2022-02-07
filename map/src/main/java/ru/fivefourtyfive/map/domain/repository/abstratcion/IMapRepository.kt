package ru.fivefourtyfive.map.domain.repository.abstratcion

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value

interface IMapRepository {

    suspend fun getArea(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ): Flow<AreaDataState>
}