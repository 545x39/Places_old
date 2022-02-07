package ru.fivefourtyfive.map.domain.repository.abstratcion

interface IMapSettingsRepository {

    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    fun getLastLocation(): Pair<Double, Double>

    fun getLastZoom(): Double

    fun getMapMode(): Int

    fun getWikimapiaOverlays(): Boolean

    fun getFollowLocation(): Boolean

    fun getCenterSelection(): Boolean

    fun getScale(): Boolean

    fun getGrid(): Boolean

    fun getKeepScreenOn(): Boolean

    fun getAutoRotateMap(): Boolean
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS">
    fun setLastZoom(zoom: Double)

    fun setLastLocation(x: Double, y: Double)

    fun setMapMode(mode: Int)

    fun setWikimapiaOverlays(enabled: Boolean)

    fun setFollowLocation(enable: Boolean)

    fun setCenterSelection(enable: Boolean)

    fun setScale(enable: Boolean)

    fun setGrid(enable: Boolean)

    fun setKeepScreenOn(enable: Boolean)

    fun setTransportationOverlay(enable: Boolean)

    fun setAutoRotateMap(enable: Boolean)
    //</editor-fold>
}