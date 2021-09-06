package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.fivefourtyfive.wikimapper.data.repository.implementation.PlaceRepository
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(private val repository: PlaceRepository): ViewModel() {

    fun getPlace(id: Int){
        repository.getPlace(id)
    }
}