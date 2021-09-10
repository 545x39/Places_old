package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.fivefourtyfive.wikimapper.data.repository.AreaRepository
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(private val repository: AreaRepository) :
    ViewModel() {

    fun getArea(latMin: Double, lonMin: Double, latMax: Double, lonMax: Double) =
        repository.getArea(latMin, lonMin, latMax, lonMax)
}