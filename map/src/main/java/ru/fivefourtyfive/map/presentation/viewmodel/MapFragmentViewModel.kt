package ru.fivefourtyfive.map.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.BoundingBox
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
import ru.fivefourtyfive.map.presentation.util.toPlacePolygon
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.places.domain.entity.dto.PlaceDTO
import ru.fivefourtyfive.places.framework.presentation.abstraction.IEventHandler
import ru.fivefourtyfive.places.framework.presentation.abstraction.IReducer
import ru.fivefourtyfive.places.util.MapMode
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
) : ViewModel(), IEventHandler<MapEvent>, IReducer<AreaDataState, MapViewState> {

    private var places = mutableListOf<PlacePolygon>()

    private val _liveData = MutableLiveData<MapViewState>(MapViewState.Loading)

    val liveData = _liveData as LiveData<MapViewState>

    var currentSelection: PlacePolygon? = null

    var mapListenerDelay = when (settings.getFollowLocation()) {
        true -> FOLLOWING_LOCATION_DELAY
        false -> DEFAULT_DELAY
    }

    var latestBearing = 0.0f

    private val latestBoundingBox = BoundingBox()

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

    @Suppress("SpellCheckingInspection")
    fun wikimapiaOverlaysEnabled() = settings.getWikimapiaOverlays()

    @Suppress("SpellCheckingInspection")
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
            latestBoundingBox.set(lonMax, latMax, lonMin, latMin)
            factory.getAreaUseCase(lonMin, latMin, lonMax, latMax)
                .execute()
                .catch { _liveData.postValue(MapViewState.Error()) }
                .collect { reduce(it).also { viewState -> _liveData.postValue(viewState) } }
        }
    }

    private fun merge(newPlaces: List<PlaceDTO>) {

        //<editor-fold defaultstate="collapsed" desc="INNER FUNCTIONS">

        fun List<PlacePolygon>.removeOutOfTheBox() = filter {
            latestBoundingBox.contains(it.north, it.west)
                    || latestBoundingBox.contains(it.north, it.east)
                    || latestBoundingBox.contains(it.south, it.west)
                    || latestBoundingBox.contains(it.south, it.east)
        }

        fun List<PlacePolygon>.removeDuplicates(idList: List<Int>) = filterNot { idList.contains(it.id) }
        //</editor-fold>

        val newPlaced = arrayListOf<PlacePolygon>()
            .apply { addAll(newPlaces.map { it.toPlacePolygon() }) }
        if ((places.isNotEmpty() && newPlaced.isEmpty())) return
        places = places.removeOutOfTheBox()
            .removeDuplicates(newPlaces.map { it.id })
            .toMutableList().apply { addAll(newPlaced) }
        val selectedId = currentSelection?.id ?: -1
        currentSelection = null
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

    override fun reduce(dataState: AreaDataState) = when (dataState) {
        is AreaDataState.Success -> {
            merge(dataState.area.places)
            MapViewState.DataLoaded()
        }
        is AreaDataState.Loading -> MapViewState.Loading
        is AreaDataState.Error -> MapViewState.Error(dataState.message)
    }
}