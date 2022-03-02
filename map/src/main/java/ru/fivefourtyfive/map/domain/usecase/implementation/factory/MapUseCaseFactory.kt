package ru.fivefourtyfive.map.domain.usecase.implementation.factory

import ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.map.domain.usecase.abstraction.factory.IMapUseCaseFactory
import javax.inject.Inject

class MapUseCaseFactory @Inject constructor(
    private val getAreaUseCase: IGetAreaUseCase
) : IMapUseCaseFactory {

    override fun getAreaUseCase(
        north: Double,
        west: Double,
        south: Double,
        east: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ): IGetAreaUseCase =
        getAreaUseCase.init(north, west, south, east, category, page, count, language)
}