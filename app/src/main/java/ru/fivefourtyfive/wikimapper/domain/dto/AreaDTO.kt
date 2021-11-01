package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.Area
import ru.fivefourtyfive.wikimapper.domain.entity.DebugInfo
import ru.fivefourtyfive.wikimapper.domain.entity.Place

class AreaDTO(area: Area) {

    var debugInfo: DebugInfo? = area.debugInfo

    val language = area.language

    val places: List<MapPlaceDTO> = getPlaces(area.places)

    val count: Int = area.count?: 0

    val found: Int = area.found ?: 0

    private fun getPlaces(places: List<Place>?): List<MapPlaceDTO> {
        return arrayListOf<MapPlaceDTO>().apply { places?.map { add(MapPlaceDTO(it)) } }
    }
}