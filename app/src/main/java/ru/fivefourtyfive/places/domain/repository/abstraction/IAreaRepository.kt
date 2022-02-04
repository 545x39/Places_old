package ru.fivefourtyfive.places.domain.repository.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.framework.datasource.remote.util.Value

interface IAreaRepository {

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