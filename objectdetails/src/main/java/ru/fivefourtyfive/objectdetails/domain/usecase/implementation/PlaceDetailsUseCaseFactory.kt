package ru.fivefourtyfive.objectdetails.domain.usecase.implementation

import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.factory.IPlaceDetailsUseCaseFactory
import javax.inject.Inject

class PlaceDetailsUseCaseFactory @Inject constructor(private val getPlaceUseCase: IGetPlaceUseCase): IPlaceDetailsUseCaseFactory {

    override fun getPlaceUseCase(id: Int, dataBlocks: String?): IGetPlaceUseCase  = getPlaceUseCase.init(id, dataBlocks)
}