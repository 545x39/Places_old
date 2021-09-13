package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.fivefourtyfive.map.presentation.util.toPlaceDto
import ru.fivefourtyfive.wikimapper.data.repository.AreaRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(private val repository: AreaRepository) :
    ViewModel(), Reducer<AreaDataState, MapViewState> {

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>

    fun getArea(latMin: Double, lonMin: Double, latMax: Double, lonMax: Double) {
        viewModelScope.launch {
            repository.getArea(latMin, lonMin, latMax, lonMax).catch {
                _liveData.postValue(MapViewState.Error())
            }.collect {
                when (it) {
                    is AreaDataState.Success -> {
                        _liveData.postValue(reduce(it))
                    }
                    is AreaDataState.Loading -> _liveData.postValue(reduce(it))
                    is AreaDataState.Error -> _liveData.postValue(reduce(it))
                }
            }
        }
    }

    override fun reduce(dataState: AreaDataState): MapViewState {
        return when (dataState) {
            is AreaDataState.Success ->
                MapViewState.Success(arrayListOf<PlaceDTO>().apply {
                    dataState.area.places.map { add(it.toPlaceDto()) }
                })
            is AreaDataState.Loading -> MapViewState.Loading
            is AreaDataState.Error -> MapViewState.Error(dataState.message)
        }
    }
}