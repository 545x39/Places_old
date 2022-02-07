package ru.fivefourtyfive.map.domain.usecase.abstraction.factory

import ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value

interface IMapUseCaseFactory {

    fun getAreaUseCase(lonMin: Double,
                       latMin: Double,
                       lonMax: Double,
                       latMax: Double,
                       category: String? = null,
                       page: Int? = 1,
                       count: Int? = Value.MAX_OBJECTS_PER_PAGE,
                       language: String? = Value.RU): IGetAreaUseCase
}