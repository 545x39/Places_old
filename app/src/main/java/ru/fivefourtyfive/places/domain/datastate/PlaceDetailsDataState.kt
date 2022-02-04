package ru.fivefourtyfive.places.domain.datastate

import ru.fivefourtyfive.places.domain.entity.dto.PlaceDescriptionDTO

sealed class PlaceDetailsDataState : DataState {
    class Success(val place: PlaceDescriptionDTO) : PlaceDetailsDataState()
    object Loading : PlaceDetailsDataState()
    class Error(val message: String? = null) : PlaceDetailsDataState()
}