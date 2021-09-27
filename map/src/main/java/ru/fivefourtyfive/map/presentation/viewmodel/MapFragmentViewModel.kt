package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MapMode
import ru.fivefourtyfive.map.presentation.util.MapSettingsUtil
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_LABELS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_STREETS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMAPIA_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMEDIA_NO_LABELS_TILE_SOURCE
import ru.fivefourtyfive.wikimapper.data.repository.implementation.AreaRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.dto.MapPlaceDTO
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventHandler
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import javax.inject.Inject
import javax.inject.Named

class MapFragmentViewModel @Inject constructor(
    private val repository: AreaRepository,
    private val settings: MapSettingsUtil,
    @Named(WIKIMEDIA_NO_LABELS_TILE_SOURCE)
    private val schemeTileSource: OnlineTileSourceBase,
    @Named(ARCGIS_IMAGERY_TILE_SOURCE)
    private val satelliteTileSource: OnlineTileSourceBase,
    @Named(ARCGIS_IMAGERY_LABELS_TILE_SOURCE)
    val labelsTileSource: OnlineTileSourceBase,
    @Named(ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE)
    val transportationTileSource: OnlineTileSourceBase,
    @Named(WIKIMAPIA_TILE_SOURCE)
    val wikimapiaTileSource: OnlineTileSourceBase
) :
    ViewModel(), Reducer<AreaDataState, MapViewState>, EventHandler<MapEvent> {

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>

    //<editor-fold defaultstate="collapsed" desc="PREFERENCES">
    //TODO Remove it when actions and view state are completed.
    fun getLastLocation(): Pair<Double, Double> = settings.getLastLocation()

    fun setLastLocation(x: Double, y: Double): MapFragmentViewModel =
        this.apply { settings.setLastLocation(x, y) }

    fun getLastZoom() = settings.getLastZoom()

    fun setLastZoom(zoom: Double): MapFragmentViewModel =
        this.apply { settings.setLastZoom(zoom) }

    fun getMapMode(): Int = settings.getMapMode()

    fun setMapMode(mode: Int) = settings.setMapMode(mode)

    fun areWikimapiaOverlayEnabled() = settings.getWikimapiaOverlays()

    fun setWikimapiaOverlays(enabled: Boolean) = settings.setWikimapiaOverlays(enabled)

    fun isFollowLocationEnabled() = settings.getFollowLocation()

    fun isCenterSelectionEnabled() = settings.getCenterSelection()

    fun isGridEnabled() = settings.getGrid()

    fun isScaleEnabled() = settings.getScale()

    fun setFollowLocation(enable: Boolean) = settings.setFollowLocation(enable)
    //</editor-fold>

    fun getTileSource() = when (settings.getMapMode()) {
        MapMode.SATELLITE -> satelliteTileSource
        else -> schemeTileSource
    }

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
            is AreaDataState.Success -> MapViewState.DataLoaded(arrayListOf<PlacePolygon>().apply {
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
            is MapEvent.SwitchMapModeEvent -> settings.setMapMode(event.mode)
            is MapEvent.SwitchWikimapiaOverlayEvent -> settings.setWikimapiaOverlays(event.enable)
            is MapEvent.SwitchTransportationOverlayEvent -> settings.setTransportationOverlay(event.enable)
            is MapEvent.SwitchFollowLocationEvent -> settings.setFollowLocation(event.enable)
            is MapEvent.SwitchCenterSelectionEvent -> settings.setCenterSelection(event.enable)
            is MapEvent.SwitchScaleEvent -> settings.setScale(event.enable)
            is MapEvent.SwitchGridEvent -> settings.setGrid(event.enable)
        }
    }
}