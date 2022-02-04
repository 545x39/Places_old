package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import kotlinx.coroutines.flow.flowOf
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IPlaceRepository
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetPlaceUseCase
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(private val repository: IPlaceRepository) :
    IGetPlaceUseCase {
    override var id: Int = 0

    override var dataBlocks: String? = null

    override suspend fun execute() =
        runCatching { return@runCatching repository.getPlace(id, dataBlocks) }.getOrDefault(
            flowOf(PlaceDetailsDataState.Error())
        )
}