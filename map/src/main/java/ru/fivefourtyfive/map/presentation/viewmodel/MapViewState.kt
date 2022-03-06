package ru.fivefourtyfive.map.presentation.viewmodel

import android.view.View
import ru.fivefourtyfive.places.framework.presentation.abstraction.IViewState

sealed class MapViewState : IViewState {
    open val progressVisibility = View.GONE

    class DataLoaded() : MapViewState()

    object Loading : MapViewState() {
        override val progressVisibility = View.VISIBLE
    }

    class Error(val message: String? = null) : MapViewState()
}
