package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import android.view.View.GONE
import android.view.View.VISIBLE
import ru.fivefourtyfive.places.domain.dto.places.CommentDTO
import ru.fivefourtyfive.places.domain.dto.places.PhotoDTO
import ru.fivefourtyfive.places.framework.presentation.abstraction.IViewState

sealed class PlaceDetailsViewState : IViewState {
    open val progressVisibility = GONE
    open val contentVisibility = GONE

    class Success(
        val id: Int,
        val title: String,
        val description: String,
        val languageIso: String,
        val photos: List<PhotoDTO>,
        val comments: List<CommentDTO>,
        val tags: List<String>,
        val lat: Double,
        val lon: Double,
        val location: String
    ) : PlaceDetailsViewState() {
        override val contentVisibility = VISIBLE
    }

    object Loading : PlaceDetailsViewState() {
        override val progressVisibility = VISIBLE
    }

    class Error(val message: String? = null) : PlaceDetailsViewState()
}