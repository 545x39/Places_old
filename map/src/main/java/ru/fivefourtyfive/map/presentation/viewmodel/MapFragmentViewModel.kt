package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapSettingsRepository
import ru.fivefourtyfive.map.domain.usecase.abstraction.factory.IMapUseCaseFactory
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.MapListenerDelay.DEFAULT_DELAY
import ru.fivefourtyfive.map.presentation.util.MapListenerDelay.FOLLOWING_LOCATION_DELAY
import ru.fivefourtyfive.map.presentation.util.Overlay
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.CARTO_VOYAGER_TILE_SOURCE
import ru.fivefourtyfive.places.framework.presentation.abstraction.IEventHandler
import ru.fivefourtyfive.places.util.MapMode
import ru.fivefourtyfive.places.util.ifFalse
import ru.fivefourtyfive.places.util.ifTrue
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MapFragmentViewModel @Inject constructor(
    private val factory: IMapUseCaseFactory,
    private val settings: IMapSettingsRepository,
    @Named(CARTO_VOYAGER_TILE_SOURCE)
    val schemeTileSource: OnlineTileSourceBase,
    @Named(ARCGIS_IMAGERY_TILE_SOURCE)
    val satelliteTileSource: OnlineTileSourceBase,
    @Named(Overlay.TRANSPORTATION_OVERLAY)
    val transportationOverlay: TilesOverlay,
    @Named(Overlay.IMAGERY_LABELS_OVERLAY)
    val imageryLabelsOverlay: TilesOverlay,
    @Suppress("SpellCheckingInspection")
    @Named(Overlay.WIKIMAPIA_OVERLAY)
    val wikimapiaOverlay: TilesOverlay,
    val myLocation: MyLocationNewOverlay,
    val gridOverlay: LatLonGridlineOverlay2,
    val folder: FolderOverlay
) : ViewModel(), IEventHandler<MapEvent> {

    private var places = listOf<PlacePolygon>()

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>

    var currentSelection: PlacePolygon? = null

    var mapListenerDelay = when (settings.getFollowLocation()) {
        true -> FOLLOWING_LOCATION_DELAY
        false -> DEFAULT_DELAY
    }

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
        lonMin: Double,
        latMin: Double,
        lonMax: Double,
        latMax: Double
    ) {
        viewModelScope.launch {
            factory.getAreaUseCase(lonMin, latMin, lonMax, latMax)
                .execute()
                .catch { _liveData.postValue(MapViewState.Error()) }
                .collect {
                    if (it is MapViewState.DataLoaded) {
                        merge(it.places)
                        _liveData.postValue(MapViewState.DataLoaded(places))
                    } else _liveData.postValue(it)
                }
        }
    }

    private fun merge(newPlaces: List<PlacePolygon>) = CoroutineScope(IO).launch {
        ((places.isNotEmpty() && newPlaces.isEmpty())).ifFalse {
            places = newPlaces
        }
        val selectedId = currentSelection?.id ?: -1
        currentSelection = null
        MapViewState.DataLoaded(places).apply {
            folder.items.apply {
                clear()
                places.map {
                    (it.id == selectedId).ifTrue {
                        currentSelection = it.apply { setHighlighted(true) }
                    }
                }
                addAll(places)
            }
        }
    }

    override fun handleEvent(event: MapEvent) {
        settings.apply {
            when (event) {
                is MapEvent.GetAreaEvent -> event.apply { getArea(lonM, latM, lonMx, latMx) }
                is MapEvent.SwitchMapModeEvent -> setMapMode(event.mode)
                is MapEvent.SwitchWikimapiaOverlayEvent -> setWikimapiaOverlays(event.enable)
                is MapEvent.SwitchTransportationOverlayEvent -> setTransportationOverlay(event.enable)
                is MapEvent.SwitchFollowLocationEvent -> setFollowLocation(event.enable)
                is MapEvent.SwitchCenterSelectionEvent -> setCenterSelection(event.enable)
                is MapEvent.SwitchScaleEvent -> setScale(event.enable)
                is MapEvent.SwitchGridEvent -> setGrid(event.enable)
            }
        }
    }
}