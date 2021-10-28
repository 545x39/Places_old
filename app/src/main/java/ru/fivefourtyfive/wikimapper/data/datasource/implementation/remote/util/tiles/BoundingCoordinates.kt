package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.tiles

import kotlin.math.max
import kotlin.math.min

class BoundingCoordinates constructor(
    @JvmField val southwest: Coordinates,
    @JvmField val northeast: Coordinates
) {

    private fun isLatitudeInBounds(latitude: Double) =
        southwest.latitude <= latitude && latitude <= northeast.latitude

    private fun longitudeInBounds(longitude: Double): Boolean {
        var result = false
        if (southwest.longitude <= northeast.longitude) return southwest.longitude <= longitude && longitude <= northeast.longitude
        if (southwest.longitude <= longitude || longitude <= northeast.longitude) result = true
        return result
    }

    operator fun contains(coordinates: Coordinates) =
        isLatitudeInBounds(coordinates.latitude) && longitudeInBounds(coordinates.longitude)

    override fun equals(other: Any?): Boolean {
        other?.let {
            return@equals when (it) {
                is BoundingCoordinates -> southwest == it.southwest && northeast == it.northeast
                else -> false
            }
        } ?: return false
    }

    fun getCenter(): Coordinates {
        val centerLatitude = (southwest.latitude + northeast.latitude) / 2.0
        val centerLongitude = when (southwest.longitude <= northeast.longitude) {
            true -> (northeast.longitude + southwest.longitude) / 2.0
            false -> (northeast.longitude + 360.0 + southwest.longitude) / 2.0
        }
        return Coordinates(centerLatitude, centerLongitude)
    }

    fun including(point: Coordinates): BoundingCoordinates {

        fun bearing(lon1: Double, lon2: Double) = (lon1 - lon2 + 360.0) - 360.0
        // Calculate new box latitude bounds
        val outLatitudes =
            min(southwest.latitude, point.latitude) to max(northeast.latitude, point.latitude)
        // Calculate new box longitude bounds
        val outLongitudes = if (longitudeInBounds(point.longitude)) {
            southwest.longitude to northeast.longitude
        } else {
            when (bearing(southwest.longitude, point.longitude)
                    >= bearing(point.longitude, northeast.longitude)) {
                //Point is to the north of current box, extend new box up to the north.
                true -> southwest.longitude to point.longitude
                //Point is to the south of current box, extend new box down to the south.
                false -> point.longitude to northeast.longitude
            }
        }
        return BoundingCoordinates(
            Coordinates(outLatitudes.first, outLongitudes.first),
            Coordinates(outLatitudes.second, outLongitudes.second)
        )
    }

    override fun hashCode() = (southwest to northeast).hashCode()

}