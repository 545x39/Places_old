package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.tiles

import kotlin.math.max
import kotlin.math.min

class Coordinates(lat: Double, lon: Double) {

    @JvmField
    var latitude: Double = 0.0

    @JvmField
    var longitude: Double = 0.0

    init {
        longitude = when (-180.0 <= lon && lon < 180.0) {
            true -> lon
            false -> ((lon - 180.0) % 360.0 + 360.0) % 360.0 - 180.0
        }
        latitude = max(-90.0, min(90.0, lat))
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            return@equals when (other) {
                is Coordinates -> latitude == (it as Coordinates).latitude && longitude == it.longitude
                else -> false
            }
        } ?: return false
    }

    override fun hashCode() = (latitude to longitude).hashCode()
}