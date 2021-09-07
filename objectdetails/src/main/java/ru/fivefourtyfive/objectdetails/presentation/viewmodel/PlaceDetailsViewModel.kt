package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.data.repository.implementation.PlaceRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDataState
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import timber.log.Timber
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(private val repository: PlaceRepository) :
    ViewModel(), Reducer<PlaceDataState, PlaceDetailsViewState> {

    private val _viewStateLiveData: MutableLiveData<PlaceDetailsViewState> =
        MutableLiveData(PlaceDetailsViewState.Loading)

    val viewStateLiveData = _viewStateLiveData as LiveData<PlaceDetailsViewState>

    fun getPlace(id: Int) {
        viewModelScope.launch {
            repository.getPlace(id)
                .catch {
                    _viewStateLiveData.postValue(reduce(PlaceDataState.Error()))
                    Timber.e("CAUGHT EXCEPTION WHILE COLLECTING DATA: [$it]")
                }.collect {
                    _viewStateLiveData.postValue(reduce(it))
                }
        }
    }

    override fun reduce(dataState: PlaceDataState): PlaceDetailsViewState {
        return when (dataState) {
            is PlaceDataState.Success -> PlaceDetailsViewState.Success(dataState.place)
            is PlaceDataState.Loading -> PlaceDetailsViewState.Loading
            is PlaceDataState.Error -> PlaceDetailsViewState.Error(dataState.message)
        }
    }
}