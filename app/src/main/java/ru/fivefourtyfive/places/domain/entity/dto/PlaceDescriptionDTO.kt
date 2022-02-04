package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.framework.datasource.remote.util.Value.RU
import ru.fivefourtyfive.places.domain.entity.DebugInfo
import ru.fivefourtyfive.places.domain.entity.Location
import ru.fivefourtyfive.places.domain.entity.Place
import ru.fivefourtyfive.places.domain.entity.Tag

class PlaceDescriptionDTO(place: Place) {

    var debugInfo: DebugInfo? = null
    val id: Int                     = place.id
    val title: String               = place.title ?: ""
    val description: String         = place.description ?: ""
    val languageIso: String         = place.languageIso ?: RU
    val wikipedia: String           = place.wikipedia ?: ""
    val tags: List<String>          = getTags(place.tags)
    val photos: List<PhotoDTO>      = getPhotos(place)
    val comments: List<CommentDTO>  = getComments(place)
    val lat                         = place.location?.lat ?: 0.0
    val lon                         = place.location?.lon ?: 0.0
    val location                    = getLocation(place.location)

    private fun getComments(place: Place) = arrayListOf<CommentDTO>().apply {
        place.comments?.map { add(CommentDTO(it)) }
    }

    private fun getPhotos(place: Place) = arrayListOf<PhotoDTO>().apply {
        place.photos?.map { add(PhotoDTO(it)) }
    }

    private fun getTags(tags: List<Tag>?): List<String> {
        return arrayListOf<String>().apply { tags?.map { add(it.title) } }
    }

    private fun getLocation(location: Location?) = location?.let { "${it.place}, ${it.country}" } ?: ""
}