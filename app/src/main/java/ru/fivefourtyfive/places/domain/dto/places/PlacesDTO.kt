package ru.fivefourtyfive.places.domain.dto.places

import ru.fivefourtyfive.places.domain.entity.places.DebugInfo
import ru.fivefourtyfive.places.domain.entity.places.Place
import ru.fivefourtyfive.places.domain.entity.places.Places

abstract class PlacesDTO<PLACE>(places: Places) {
    var debugInfo: DebugInfo? = places.debugInfo

    val language = places.language

    open val places: List<PLACE> = getPlaces(places.places)

    val count: Int = places.count?: 0

    val found: Int = places.found ?: 0

    private fun getPlaces(places: List<Place>?): List<PLACE> {
        return arrayListOf<PLACE>().apply { places?.map { add(convert(it)) } }
    }

    protected abstract fun convert(place: Place): PLACE
}