package ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.factory

import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.IGetPlaceUseCase

interface IPlaceDetailsUseCaseFactory {

    fun getPlaceUseCase(id: Int, dataBlocks: String? = null): IGetPlaceUseCase
}