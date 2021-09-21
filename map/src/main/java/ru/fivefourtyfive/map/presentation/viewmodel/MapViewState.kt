package ru.fivefourtyfive.map.presentation.viewmodel

import android.view.View
import ru.fivefourtyfive.map.presentation.dto.PlacePolygon
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.ViewState

sealed class MapViewState : ViewState {
    open val progressVisibility = View.GONE

    class Success(val places: ArrayList<PlacePolygon>) : MapViewState()

    object Loading : MapViewState() {
        override val progressVisibility = View.VISIBLE
    }

    class Error(val message: String? = null) : MapViewState()
}