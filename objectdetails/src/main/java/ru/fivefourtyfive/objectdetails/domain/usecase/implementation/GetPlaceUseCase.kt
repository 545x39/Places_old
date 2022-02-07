package ru.fivefourtyfive.objectdetails.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceRepository
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewState
import ru.fivefourtyfive.objectdetails.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.places.framework.presentation.abstraction.IReducer
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(private val repository: IPlaceRepository) :
    IGetPlaceUseCase, IReducer<PlaceDetailsDataState, PlaceDetailsViewState> {

    override var id: Int = 0

    override var dataBlocks: String? = null

    override suspend fun execute() = flow {
        repository.getPlace(id, dataBlocks).flowOn(IO)
            .catch { emit(PlaceDetailsViewState.Error()) }
            .map { emit(reduce((it))) }
            .flowOn(Main)
    }

    override fun reduce(dataState: PlaceDetailsDataState): PlaceDetailsViewState {
        with(dataState) {
            return when (this) {
                is PlaceDetailsDataState.Success -> PlaceDetailsViewState.Success(
                    id          = place.id,
                    title       = place.title,
                    description = place.description,
                    languageIso = place.languageIso,
                    photos      = place.photos,
                    comments    = place.comments,
                    tags        = place.tags,
                    lat         = place.lat,
                    lon         = place.lon,
                    location    = place.location
                )
                is PlaceDetailsDataState.Loading -> PlaceDetailsViewState.Loading
                is PlaceDetailsDataState.Error -> PlaceDetailsViewState.Error(message = message)
            }
        }
    }
}