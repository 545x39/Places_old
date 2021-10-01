package ru.fivefourtyfive.map.presentation.util

import android.content.SharedPreferences
import ru.fivefourtyfive.wikimapper.util.Preferences
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_CENTER_SELECTION
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_FOLLOW_LOCATION
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_KEEP_SCREEN_ON
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_LAST_ZOOM
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_MAP_MODE
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_SHOW_GRID
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_SHOW_SCALE
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_TRANSPOTRATION_OVERLAY
import ru.fivefourtyfive.wikimapper.util.Preferences.PREFERENCE_WIKIMAPIA_OVERLAYS
import ru.fivefourtyfive.wikimapper.util.SettingsUtil
import javax.inject.Inject

class MapSettingsUtil @Inject constructor(preferences: SharedPreferences) : SettingsUtil(preferences) {

    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    fun getLastLocation() = getPairOfDouble(Preferences.PREFERENCE_LAST_LOCATION, Preferences.DEFAULT_LOCATION)

    fun getLastZoom() = preferences.getFloat(PREFERENCE_LAST_ZOOM, Zoom.ZOOM_DEFAULT.toFloat()).toDouble()

    fun getMapMode(): Int = preferences.getInt(PREFERENCE_MAP_MODE, MapMode.SCHEME)

    fun getWikimapiaOverlays() = preferences.getBoolean(PREFERENCE_WIKIMAPIA_OVERLAYS, true)

    fun getFollowLocation() = preferences.getBoolean(PREFERENCE_FOLLOW_LOCATION, false)

    fun getCenterSelection() = preferences.getBoolean(PREFERENCE_CENTER_SELECTION, false)

    fun getScale() = preferences.getBoolean(PREFERENCE_SHOW_SCALE, true)

    fun getGrid() = preferences.getBoolean(PREFERENCE_SHOW_GRID, false)

    fun getKeepScreenOn() = preferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, true)
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS">
    fun setLastZoom(zoom: Double) = putDouble(PREFERENCE_LAST_ZOOM, zoom)

    fun setLastLocation(x: Double, y: Double) = putString(Preferences.PREFERENCE_LAST_LOCATION, "$x;$y")

    fun setMapMode(mode: Int) = putInt(PREFERENCE_MAP_MODE, mode)

    fun setWikimapiaOverlays(enabled: Boolean) = putBoolean(PREFERENCE_WIKIMAPIA_OVERLAYS, enabled)

    fun setFollowLocation(enable: Boolean) = putBoolean(PREFERENCE_FOLLOW_LOCATION, enable)

    fun setCenterSelection(enable: Boolean) = putBoolean(PREFERENCE_CENTER_SELECTION, enable)

    fun setScale(enable: Boolean) = putBoolean(PREFERENCE_SHOW_SCALE, enable)

    fun setGrid(enable: Boolean) = putBoolean(PREFERENCE_SHOW_GRID, enable)

    fun setKeepScreenOn(enable: Boolean) = putBoolean(PREFERENCE_KEEP_SCREEN_ON, enable)

    fun setTransportationOverlay(enable: Boolean) = putBoolean(PREFERENCE_TRANSPOTRATION_OVERLAY, enable)
    //</editor-fold>
}