package ru.fivefourtyfive.wikimapper.domain.datastate

import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO

sealed class PlaceDetailsDataState : DataState {
    class Success(val place: PlaceDescriptionDTO) : PlaceDetailsDataState()
    object Loading : PlaceDetailsDataState()
    class Error(val message: String? = null) : PlaceDetailsDataState()
}