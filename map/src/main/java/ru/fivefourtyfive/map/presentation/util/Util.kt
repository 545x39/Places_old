package ru.fivefourtyfive.map.presentation.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.osmdroid.util.GeoPoint
import ru.fivefourtyfive.map.presentation.dto.PlaceDTO
import ru.fivefourtyfive.wikimapper.domain.entity.Place

suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> = coroutineScope { map { async { f(it) } }.awaitAll() }

fun <T> Boolean.ifTrue(block: () -> T): T? = when (this) {
    true -> block()
    false -> null
}

fun <T> Boolean.ifFalse(block: () -> T): T? = this.not().ifTrue(block)

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