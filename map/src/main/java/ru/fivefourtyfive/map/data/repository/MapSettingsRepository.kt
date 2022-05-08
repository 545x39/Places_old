package ru.fivefourtyfive.map.data.repository

import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapSettingsRepository
import ru.fivefourtyfive.places.util.MapMode.SCHEME
import ru.fivefourtyfive.places.util.MapZoom.ZOOM_DEFAULT
import ru.fivefourtyfive.places.util.Preferences
import javax.inject.Inject

class MapSettingsRepository @Inject constructor(private val settings: ISettingsDataSource):
    IMapSettingsRepository {

    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    override fun getLastLocation() = settings.getPairOfDouble(Preferences.PREFERENCE_LAST_LOCATION, Preferences.DEFAULT_LOCATION)

    override fun getLastZoom() = settings.getFloat(Preferences.PREFERENCE_LAST_ZOOM, ZOOM_DEFAULT.toFloat()).toDouble()

    override fun getMapMode(): Int = settings.getInt(Preferences.PREFERENCE_MAP_MODE, SCHEME)

    override fun getWikimapiaOverlays(): Boolean = settings.getBoolean(Preferences.PREFERENCE_WIKIMAPIA_OVERLAYS, true)

    override fun getFollowLocation() = settings.getBoolean(Preferences.PREFERENCE_FOLLOW_LOCATION, false)

    override fun getCenterSelection() = settings.getBoolean(Preferences.PREFERENCE_CENTER_SELECTION, false)

    override fun getScale() = settings.getBoolean(Preferences.PREFERENCE_SHOW_SCALE, true)

    override fun getGrid() = settings.getBoolean(Preferences.PREFERENCE_SHOW_GRID, false)

    override fun getKeepScreenOn() = settings.getBoolean(Preferences.PREFERENCE_KEEP_SCREEN_ON, true)

    override fun getAutoRotateMap() = settings.getBoolean(Preferences.PREFERENCE_AUTO_ROTATE_MAP, false)
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS">
    override fun setLastZoom(zoom: Double) = settings.putDouble(Preferences.PREFERENCE_LAST_ZOOM, zoom)

    override fun setLastLocation(x: Double, y: Double) = settings.putPairOfDouble(Preferences.PREFERENCE_LAST_LOCATION, x to y)

    override fun setMapMode(mode: Int) = settings.putInt(Preferences.PREFERENCE_MAP_MODE, mode)

    override fun setWikimapiaOverlays(enabled: Boolean) = settings.putBoolean(Preferences.PREFERENCE_WIKIMAPIA_OVERLAYS, enabled)

    override fun setFollowLocation(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_FOLLOW_LOCATION, enable)

    override fun setCenterSelection(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_CENTER_SELECTION, enable)

    override fun setScale(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_SHOW_SCALE, enable)

    override fun setGrid(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_SHOW_GRID, enable)

    override fun setKeepScreenOn(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_KEEP_SCREEN_ON, enable)

    override fun setTransportationOverlay(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_TRANSPOTRATION_OVERLAY, enable)

    override fun setAutoRotateMap(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_AUTO_ROTATE_MAP, enable)

    override fun getFilterLocationAccuracy() = settings.getBoolean(Preferences.PREFERENCE_FILTER_LOCATION_ACCURACY)

    override fun setFilterLocationAccuracy(enable: Boolean) = settings.putBoolean(Preferences.PREFERENCE_FILTER_LOCATION_ACCURACY, enable)
    //</editor-fold>
}