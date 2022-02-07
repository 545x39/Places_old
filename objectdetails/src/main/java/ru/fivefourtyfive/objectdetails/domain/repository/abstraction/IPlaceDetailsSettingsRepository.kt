package ru.fivefourtyfive.objectdetails.domain.repository.abstraction

interface IPlaceDetailsSettingsRepository {

    fun enableSlideShow(enable: Boolean)

    fun isSlideshowEnabled(): Boolean
}