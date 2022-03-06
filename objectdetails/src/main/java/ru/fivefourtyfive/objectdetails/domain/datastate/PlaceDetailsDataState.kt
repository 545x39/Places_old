package ru.fivefourtyfive.objectdetails.domain.datastate

import ru.fivefourtyfive.places.domain.datastate.DataState
import ru.fivefourtyfive.places.domain.dto.places.PlaceDescriptionDTO

sealed class PlaceDetailsDataState : DataState {
    class Success(val place: PlaceDescriptionDTO) : PlaceDetailsDataState()
    object Loading : PlaceDetailsDataState()
    class Error(val message: String? = null) : PlaceDetailsDataState()
}