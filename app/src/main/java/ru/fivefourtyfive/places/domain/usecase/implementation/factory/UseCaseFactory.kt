package ru.fivefourtyfive.places.domain.usecase.implementation.factory

import ru.fivefourtyfive.places.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.places.domain.usecase.abstraction.factory.IUseCaseFactory
import javax.inject.Inject

class UseCaseFactory @Inject constructor(
    private val getAreaUseCase: IGetAreaUseCase
) : IUseCaseFactory {

    override fun getAreaUseCase(
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double,
        category: String?,
        page: Int?,
        count: Int?,
        language: String?
    ): IGetAreaUseCase =
        getAreaUseCase.init(lonMin, latMin, lonMax, latMax, category, page, count, language)

}