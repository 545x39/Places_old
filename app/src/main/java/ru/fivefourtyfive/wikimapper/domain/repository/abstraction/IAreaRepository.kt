package ru.fivefourtyfive.wikimapper.domain.repository.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.framework.datasource.remote.util.Value

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