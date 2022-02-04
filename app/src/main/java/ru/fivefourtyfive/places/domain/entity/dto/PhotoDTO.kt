package ru.fivefourtyfive.places.domain.entity.dto

import ru.fivefourtyfive.places.domain.entity.Photo

class PhotoDTO(photo: Photo) {

    val url1280 = photo.url1280
//  Will need it for fullscreen photo viewer.
//  val fullUrl = photo.fullUrl
    val description = "${photo.userName}, ${photo.timeString}"
}