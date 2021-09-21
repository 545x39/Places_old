package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.wikimapper.data.repository.implementation.AreaRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.dto.MapPlaceDTO
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventHandler
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(private val repository: AreaRepository) :
    ViewModel(), Reducer<AreaDataState, MapViewState>, EventHandler<MapEvent> {

    private val channel = BroadcastChannel<MapEvent>(Channel.CONFLATED)

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
        fun MapPlaceDTO.toPlacePolygon() = PlacePolygon(
            id = id,
            name = title,
            url = url,
            north = north,
            south = south,
            east = east,
            west = west,
            lat = lat,
            lon = lon,
            polygon = arrayListOf<GeoPoint>().apply {
                polygon.map { add(GeoPoint(it.y, it.x)) }
            }
        )

        return when (dataState) {
            is AreaDataState.Success -> MapViewState.Success(arrayListOf<PlacePolygon>().apply {
                dataState.area.places.map { add(it.toPlacePolygon()) }
            })
            is AreaDataState.Loading -> MapViewState.Loading
            is AreaDataState.Error -> MapViewState.Error(dataState.message)
        }
    }

    override fun handleEvent(event: MapEvent) {
        when (event) {
            is MapEvent.GetAreaEvent -> {
                event.apply { getArea(latMin, lonMin, latMax, lonMax) }
            }
        }
    }
}