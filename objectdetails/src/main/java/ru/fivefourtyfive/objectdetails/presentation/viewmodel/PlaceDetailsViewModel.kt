package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.factory.IPlaceDetailsUseCaseFactory
import ru.fivefourtyfive.places.framework.presentation.abstraction.EventHandler
import ru.fivefourtyfive.places.util.Preferences.PREFERENCE_SLIDESHOW
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(
    private val factory: IPlaceDetailsUseCaseFactory,
    private val preferences: SharedPreferences
) : ViewModel(), EventHandler<PlaceEvent> {

    private val _viewStateLiveData: MutableLiveData<PlaceDetailsViewState> =
        MutableLiveData(PlaceDetailsViewState.Loading)

    val viewStateLiveData = _viewStateLiveData as LiveData<PlaceDetailsViewState>

    override fun handleEvent(event: PlaceEvent) {
        when (event) {
            is PlaceEvent.GetPlace ->viewModelScope.launch {
                factory.getPlaceUseCase(event.id).execute().collect {
                    _viewStateLiveData.postValue(it)
                }
            }
            is PlaceEvent.SetSlideshow -> preferences.edit().putBoolean(PREFERENCE_SLIDESHOW, event.enable).apply()
        }
    }

    fun slideshow() = preferences.getBoolean(PREFERENCE_SLIDESHOW, false)
}