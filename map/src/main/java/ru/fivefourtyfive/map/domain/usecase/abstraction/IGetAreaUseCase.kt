package ru.fivefourtyfive.map.domain.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value

interface IGetAreaUseCase : IUseCase<Flow<AreaDataState>> {

    var north: Double
    var west: Double
    var south: Double
    var east: Double
    var category: String?
    var page: Int?
    var count: Int?
    var language: String?

    fun init(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String? = null,
        page: Int? = 1,
        count: Int? = Value.MAX_OBJECTS_PER_PAGE,
        language: String? = Value.RU
    ) = this.apply {
        this.north = north
        this.west = west
        this.south = south
        this.east = east
        this.category = category
        this.page = page
        this.count = count
        this.language = language
    }
}