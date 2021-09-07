package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.wikimapper.data.repository.implementation.PlaceRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDataState
import timber.log.Timber
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(private val repository: PlaceRepository) :
    ViewModel() {

    fun getPlace(id: Int) {
        viewModelScope.launch {
            repository.getPlace(id)
                .catch {
                    Timber.e("CAUGHT EXCEPTION WHILE COLLECTING DATA: [$it]")
                }.collect {
                    when (it) {
                        is PlaceDataState.Success -> Timber.e("DATA COLLECTED: [${it.place.title}]")
                        is PlaceDataState.Loading -> Timber.e("DATA IS NOW BEING LOADED...")
                        is PlaceDataState.Error -> Timber.e("ERROR COLLECTING DATA: [${it.message}]")
                    }
                }
        }
    }
}