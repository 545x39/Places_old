package ru.fivefourtyfive.wikimapper.domain.dto

import ru.fivefourtyfive.wikimapper.domain.entity.DebugInfo
import ru.fivefourtyfive.wikimapper.domain.entity.PolygonPoint
import ru.fivefourtyfive.wikimapper.domain.entity.Place

open class PlaceDTO(place: Place) {
    var debugInfo: DebugInfo?       = place.debugInfo
    val id: Int                     = place.id
    val url: String                 = place.url ?: ""
    val title: String               = place.name ?: ""
    val polygon: List<PolygonPoint> = place.polygon ?: listOf()
    val north                       = place.location?.north ?: 0.0
    val south                       = place.location?.south ?: 0.0
    val east                        = place.location?.east ?: 0.0
    val west                        = place.location?.west ?: 0.0
    val lat                         = place.location?.lat ?: 0.0
    val lon                         = place.location?.lon ?: 0.0

}