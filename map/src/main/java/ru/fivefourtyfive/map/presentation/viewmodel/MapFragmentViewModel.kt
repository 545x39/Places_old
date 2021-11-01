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
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MapListenerDelay.DEFAULT_DELAY
import ru.fivefourtyfive.map.presentation.util.MapListenerDelay.FOLLOWING_LOCATION_DELAY
import ru.fivefourtyfive.map.presentation.util.MapMode
import ru.fivefourtyfive.map.presentation.util.MapSettingsUtil
import ru.fivefourtyfive.map.presentation.util.Overlay
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMEDIA_NO_LABELS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.toPlacePolygon
import ru.fivefourtyfive.wikimapper.data.repository.AreaRepository
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.EventHandler
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Reducer
import ru.fivefourtyfive.wikimapper.util.ifTrue
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MapFragmentViewModel @Inject constructor(
    private val repository: AreaRepository,
    private val settings: MapSettingsUtil,
    @Named(WIKIMEDIA_NO_LABELS_TILE_SOURCE)
    val schemeTileSource: OnlineTileSourceBase,
    @Named(ARCGIS_IMAGERY_TILE_SOURCE)
    val satelliteTileSource: OnlineTileSourceBase,
    @Named(Overlay.TRANSPORTATION_OVERLAY)
    val transportationOverlay: TilesOverlay,
    @Named(Overlay.IMAGERY_LABELS_OVERLAY)
    val imageryLabelsOverlay: TilesOverlay,
    @Named(Overlay.WIKIMAPIA_OVERLAY)
    val wikimapiaOverlay: TilesOverlay,
    val myLocation: MyLocationNewOverlay,
    val gridOverlay: LatLonGridlineOverlay2,
    val folder: FolderOverlay,
//    val places: ArrayList<PlacePolygon>,
//    val labels: ArrayList<IGeoPoint>
) : ViewModel(), Reducer<AreaDataState, MapViewState>, EventHandler<MapEvent> {

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>

    var currentSelection: PlacePolygon? = null

    fun getMapListenerDelay() =
        if (settings.getFollowLocation()) DEFAULT_DELAY else FOLLOWING_LOCATION_DELAY

    var latestBearing = 0.0f

    //<editor-fold defaultstate="collapsed" desc="PREFERENCES">
    fun getLastLocation() =
        GeoPoint(settings.getLastLocation().first, settings.getLastLocation().second)

    fun setLastLocation(x: Double, y: Double): MapFragmentViewModel =
        this.apply { settings.setLastLocation(x, y) }

    fun getLastZoom() = settings.getLastZoom()

    fun setLastZoom(zoom: Double): MapFragmentViewModel =
        this.apply {
            settings.setLastZoom(zoom)
            Timber.e("=================== ZOOM SET TO: $zoom")
        }

    fun getMapMode(): Int = settings.getMapMode()

    fun setMapMode(mode: Int) = settings.setMapMode(mode)

    fun wikimapiaOverlaysEnabled() = settings.getWikimapiaOverlays()

    fun setWikimapiaOverlays(enabled: Boolean) = settings.setWikimapiaOverlays(enabled)

    fun isFollowLocationEnabled() = settings.getFollowLocation()

    fun isCenterSelectionEnabled() = settings.getCenterSelection()

    fun isGridEnabled() = settings.getGrid()

    fun isScaleEnabled() = settings.getScale()

    fun setFollowLocation(enable: Boolean) = settings.setFollowLocation(enable)

    fun isKeepScreenOnEnabled() = settings.getKeepScreenOn()

    fun setKeepScreenOn(enable: Boolean) = settings.setKeepScreenOn(enable)

    fun isAutoRotateMapEnabled() = settings.getAutoRotateMap()

    fun setAutoRotateMap(enable: Boolean) = settings.setAutoRotateMap(enable)
    //</editor-fold>

    fun getTileSource() =
        if (settings.getMapMode() == MapMode.SATELLITE) satelliteTileSource else schemeTileSource

    private fun getArea(
        latMin: Double,
        lonMin: Double,
        latMax: Double,
        lonMax: Double
    ) = viewModelScope.launch {
        repository.getArea(latMin, lonMin, latMax, lonMax)
            .catch { _liveData.postValue(MapViewState.Error()) }
            .collect { _liveData.postValue(reduce(it))}
    }


    private fun onSuccess(dataState: AreaDataState.Success) = MapViewState.DataLoaded().apply {
        folder.items.apply {
            clear()
            dataState.area.places.map {
                add(
                    it.toPlacePolygon()
                        .apply { (this == currentSelection).ifTrue { setHighlighted(true) } })
            }
        }
    }

    override fun reduce(dataState: AreaDataState) = when (dataState) {
        is AreaDataState.Success -> onSuccess(dataState)
        is AreaDataState.Loading -> MapViewState.Loading
        is AreaDataState.Error -> MapViewState.Error(dataState.message)
    }

    override fun handleEvent(event: MapEvent) {
        when (event) {
            is MapEvent.GetAreaEvent -> event.apply { getArea(latMin, lonMin, latMax, lonMax) }
            is MapEvent.SwitchMapModeEvent -> settings.setMapMode(event.mode)
            is MapEvent.SwitchWikimapiaOverlayEvent -> settings.setWikimapiaOverlays(event.enable)
            is MapEvent.SwitchTransportationOverlayEvent -> settings.setTransportationOverlay(
                event.enable
            )
            is MapEvent.SwitchFollowLocationEvent -> settings.setFollowLocation(event.enable)
            is MapEvent.SwitchCenterSelectionEvent -> settings.setCenterSelection(event.enable)
            is MapEvent.SwitchScaleEvent -> settings.setScale(event.enable)
            is MapEvent.SwitchGridEvent -> settings.setGrid(event.enable)
        }
    }
}