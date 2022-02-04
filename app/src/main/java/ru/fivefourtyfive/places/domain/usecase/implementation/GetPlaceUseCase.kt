package ru.fivefourtyfive.places.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.places.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.places.domain.repository.abstraction.IPlaceRepository
import ru.fivefourtyfive.places.domain.usecase.abstraction.IGetPlaceUseCase
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(private val repository: IPlaceRepository) :
    IGetPlaceUseCase {

    override var id: Int = 0

    override var dataBlocks: String? = null

    override suspend fun execute() =
        runCatching { return@runCatching repository.getPlace(id, dataBlocks) }.getOrDefault(
            flowOf(PlaceDetailsDataState.Error()).flowOn(IO)
        )
}