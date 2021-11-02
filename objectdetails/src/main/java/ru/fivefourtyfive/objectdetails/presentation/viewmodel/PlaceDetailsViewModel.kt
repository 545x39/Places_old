package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.data.repository.PlaceRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventHandler
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_SLIDESHOW
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(
    private val repository: PlaceRepository,
    private val preferences: SharedPreferences
) :
    ViewModel(), Reducer<PlaceDetailsDataState, PlaceDetailsViewState>, EventHandler<PlaceEvent> {

    private val _viewStateLiveData: MutableLiveData<PlaceDetailsViewState> =
        MutableLiveData(PlaceDetailsViewState.Loading)

    val viewStateLiveData = _viewStateLiveData as LiveData<PlaceDetailsViewState>

    override fun handleEvent(event: PlaceEvent) {
        when (event) {
            is PlaceEvent.GetPlace -> getPlace(event.id)
            is PlaceEvent.SetSlideshow -> setSlideshow(event.enable)
        }
    }

    fun slideshow() = preferences.getBoolean(PREFERENCE_SLIDESHOW, false)

    private fun setSlideshow(enabled: Boolean) =
        preferences.edit().putBoolean(PREFERENCE_SLIDESHOW, enabled).apply()

    fun getPlace(id: Int) {
        viewModelScope.launch {
            repository.getPlace(id)
                .catch {
                    _viewStateLiveData.postValue(reduce(PlaceDetailsDataState.Error()))
                }.collect {
                    _viewStateLiveData.postValue(reduce(it))
                }
        }
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