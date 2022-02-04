package ru.fivefourtyfive.wikimapper.domain.usecase.implementation.factory

import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.factory.IUseCaseFactory
import javax.inject.Inject

class UseCaseFactory @Inject constructor(
    private val getAreaUseCase: IGetAreaUseCase,
    private val getPlaceUseCase: IGetPlaceUseCase
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

    override fun getPlaceUseCase(id: Int, dataBlocks: String?): IGetPlaceUseCase {
        TODO("Not yet implemented")
    }

}