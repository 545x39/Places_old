package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.DebugInfo
import ru.fivefourtyfive.wikimapper.domain.entity.PolyPoint
import ru.fivefourtyfive.wikimapper.domain.entity.Place

class MapPlaceDTO(place: Place) {
    var debugInfo: DebugInfo?       = place.debugInfo
    val id: Int                     = place.id
    val url: String                 = place.url ?: ""
    val title: String               = place.name ?: ""
    val polygon: List<PolyPoint>    = place.polygon ?: listOf()
    val north                       = place.location?.north ?: 0.0
    val south                       = place.location?.south ?: 0.0
    val east                        = place.location?.east ?: 0.0
    val west                        = place.location?.west ?: 0.0
    val lat                         = place.location?.lat ?: 0.0
    val lon                         = place.location?.lon ?: 0.0
}