package ru.fivefourtyfive.wikimapper.domain.datastate

import ru.fivefourtyfive.wikimapper.domain.entity.Place

sealed class PlaceDataState : DataState {
    class Success(val place: Place) : PlaceDataState()
    object Loading : PlaceDataState()
    class Error(val message: String? = null) : PlaceDataState()
}