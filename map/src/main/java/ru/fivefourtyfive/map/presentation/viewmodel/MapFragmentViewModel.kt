package ru.fivefourtyfive.map.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.ZOOM_DEFAULT
import ru.fivefourtyfive.wikimapper.data.repository.implementation.AreaRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.dto.MapPlaceDTO
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventHandler
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import ru.fivefourtyfive.wikimapper.util.Preferences.DEFAULT_LOCATION
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_LAST_LOCATION
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_LAST_ZOOM
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(
    private val repository: AreaRepository,
    private val preferences: SharedPreferences
) :
    ViewModel(), Reducer<AreaDataState, MapViewState>, EventHandler<MapEvent> {

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>


    fun getLastLocation(): Pair<Double, Double> {
        preferences.getString(PREFERENCE_LAST_LOCATION, DEFAULT_LOCATION)!!.split(";").apply {
            return this[0].toDouble() to this[1].toDouble()
        }
    }

    fun setLastLocation(x: Double, y: Double): MapFragmentViewModel =
        this.apply { preferences.edit().putString(PREFERENCE_LAST_LOCATION, "$x;$y").apply() }

    fun getLastZoom() =
        preferences.getFloat(PREFERENCE_LAST_ZOOM, ZOOM_DEFAULT.toFloat()).toDouble()

    fun setLastZoom(zoom: Double): MapFragmentViewModel =
        this.apply { preferences.edit().putFloat(PREFERENCE_LAST_ZOOM, zoom.toFloat()).apply() }

    fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double
    ): MapFragmentViewModel = this.apply {
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