package ru.fivefourtyfive.places.domain.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value

interface IGetAreaUseCase : IUseCase<Flow<AreaDataState>> {

    var lonMin: Double
    var latMin: Double
    var lonMax: Double
    var latMax: Double
    var category: String?
    var page: Int?
    var count: Int?
    var language: String?

    fun init(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = this.apply {
        this.lonMin = lonMin
        this.latMin = latMin
        this.lonMax = lonMax
        this.latMax = latMax
        this.category = category
        this.page = page
        this.count = count
        this.language = language
    }
}