package ru.fivefourtyfive.objectdetails.domain.usecase.abstraction

import kotlinx.coroutines.flow.Flow
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.places.domain.usecase.abstraction.IUseCase

interface IGetPlaceUseCase : IUseCase<Flow<PlaceDetailsViewState>> {

    var id: Int

    var dataBlocks: String?


    fun init(id: Int = 1, dataBlocks: String? = null) = this.apply {
        this.id = id
        this.dataBlocks = dataBlocks
    }
}