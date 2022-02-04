package ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.factory

import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.wikimapper.framework.datasource.remote.util.Value

interface IUseCaseFactory {

    fun getAreaUseCase(lonMin: Double,
                       latMin: Double,
                       lonMax: Double,
                       latMax: Double,
                       category: String? = null,
                       page: Int? = 1,
                       count: Int? = Value.MAX_OBJECTS_PER_PAGE,
                       language: String? = Value.RU): IGetAreaUseCase

    fun getPlaceUseCase(id: Int, dataBlocks: String?): IGetPlaceUseCase

}