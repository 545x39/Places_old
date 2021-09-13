package ru.fivefourtyfive.map.presentation.util

import org.osmdroid.util.GeoPoint
import ru.fivefourtyfive.map.presentation.viewmodel.PlaceDTO
import ru.fivefourtyfive.wikimapper.domain.entity.Place

fun Place.toPlaceDto() = PlaceDTO(
    id = id,
    name = name ?: "",
    url = url ?: "",
    north = location?.north ?: 0.0,
    south = location?.south ?: 0.0,
    east = location?.east ?: 0.0,
    west = location?.west ?: 0.0,
    lat = location?.lat ?: 0.0,
    lon = location?.lon ?: 0.0,
    polygon = arrayListOf<GeoPoint>().apply { polygon?.map { add(GeoPoint(it.y, it.x)) } }
)