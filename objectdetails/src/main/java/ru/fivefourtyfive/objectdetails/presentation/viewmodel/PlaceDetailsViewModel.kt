package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.factory.IPlaceDetailsUseCaseFactory
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceDetailsSettingsRepository
import ru.fivefourtyfive.places.framework.presentation.abstraction.IEventHandler
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(
    private val factory: IPlaceDetailsUseCaseFactory,
    private val settings: IPlaceDetailsSettingsRepository
) : ViewModel(), IEventHandler<PlaceEvent> {

    private val _viewStateLiveData: MutableLiveData<PlaceDetailsViewState> =
        MutableLiveData(PlaceDetailsViewState.Loading)

    val viewStateLiveData = _viewStateLiveData as LiveData<PlaceDetailsViewState>

    val slideshow: Boolean
        get() = settings.isSlideshowEnabled()

    override fun handleEvent(event: PlaceEvent) {
        when (event) {
            is PlaceEvent.GetPlace -> viewModelScope.launch {
                factory.getPlaceUseCase(event.id).execute().collect {
                    _viewStateLiveData.postValue(it)
                }
            }
            is PlaceEvent.SetSlideshow -> settings.enableSlideShow(event.enable)
        }
    }
}