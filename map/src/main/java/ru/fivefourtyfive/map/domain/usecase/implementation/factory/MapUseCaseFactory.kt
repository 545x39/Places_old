package ru.fivefourtyfive.map.domain.usecase.implementation.factory

import ru.fivefourtyfive.map.domain.usecase.abstraction.factory.IMapUseCaseFactory
import javax.inject.Inject

class MapUseCaseFactory @Inject constructor(private val getAreaUseCase: ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
    ) : IMapUseCaseFactory {

        override fun getAreaUseCase(
            lonMin: Double,
            latMin: Double,
            lonMax: Double,
            latMax: Double,
            category: String?,
            page: Int?,
            count: Int?,
            language: String?
        ): ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase =
            getAreaUseCase.init(lonMin, latMin, lonMax, latMax, category, page, count, language)

    }