package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import android.view.View.GONE
import android.view.View.VISIBLE
import ru.fivefourtyfive.wikimapper.data.datasource.remote.util.Value.RU
import ru.fivefourtyfive.wikimapper.domain.entity.Comment
import ru.fivefourtyfive.wikimapper.domain.entity.Photo
import ru.fivefourtyfive.wikimapper.domain.entity.Tag
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.ViewState

sealed class PlaceDetailsViewState : ViewState {
    open val progressVisibility = GONE
    open val contentVisibility = GONE

    class Success(
        val id: Int,
        title: String?,
        description: String?,
        languageIso: String?,
        photos: List<Photo>?,
        comments: List<Comment>?,
        tags: List<Tag>?,
        lat: Double?,
        lon: Double?,
        place: String?,
        country: String?
    ) : PlaceDetailsViewState() {
        override val contentVisibility = VISIBLE
        val title = title ?: ""
        val description = description ?: ""
        val languageIso: String = languageIso ?: RU
        val photos: List<Photo> = photos ?: listOf()
        val comments: List<Comment> = comments ?: listOf()
        val tags: List<String> = tagsToStrings(tags)
        val lat: Double = lat ?: 0.0
        val lon: Double = lon ?: 0.0
        val place: String = place ?: ""
        val country: String = country ?: ""

        private fun tagsToStrings(tags: List<Tag>?): List<String> {
            return arrayListOf<String>().apply { tags?.map { add(it.title) } }
        }
    }

    object Loading : PlaceDetailsViewState() {
        override val progressVisibility = VISIBLE
    }

    class Error(val message: String? = null) : PlaceDetailsViewState()
}