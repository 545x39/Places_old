package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.fivefourtyfive.wikimapper.data.repository.implementation.ObjectRepository
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor(private val repository: ObjectRepository): ViewModel() {

    fun getPlace(id: Int){
        repository.getPlace(id)
    }
}