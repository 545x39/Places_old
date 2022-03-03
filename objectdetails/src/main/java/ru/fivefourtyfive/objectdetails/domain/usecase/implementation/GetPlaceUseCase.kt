package ru.fivefourtyfive.objectdetails.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import ru.fivefourtyfive.objectdetails.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceDetailsRepository
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.places.framework.presentation.abstraction.IReducer
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(private val repository: IPlaceDetailsRepository) :
    IGetPlaceUseCase, IReducer<PlaceDetailsDataState, PlaceDetailsViewState> {

    override var id: Int = 0

    override var dataBlocks: String? = null

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(FlowPreview::class)
    override suspend fun execute(): Flow<PlaceDetailsViewState> =
        repository.getPlace(id, dataBlocks)
            .flatMapConcat { flow { emit(reduce(it)) } }
            .catch { emit(PlaceDetailsViewState.Error()) }
            .flowOn(IO)

    override fun reduce(dataState: PlaceDetailsDataState): PlaceDetailsViewState {
        Dispatchers.Default
        with(dataState) {
            return when (this) {
                is PlaceDetailsDataState.Success -> PlaceDetailsViewState.Success(
                    id = place.id,
                    title = place.title,
                    description = place.description,
                    languageIso = place.languageIso,
                    photos = place.photos,
                    comments = place.comments,
                    tags = place.tags,
                    lat = place.lat,
                    lon = place.lon,
                    location = place.location
                )
                is PlaceDetailsDataState.Loading -> PlaceDetailsViewState.Loading
                is PlaceDetailsDataState.Error -> PlaceDetailsViewState.Error(message = message)
            }
        }
    }
}