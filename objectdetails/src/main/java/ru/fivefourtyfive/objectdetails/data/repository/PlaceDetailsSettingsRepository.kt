package ru.fivefourtyfive.objectdetails.data.repository

import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceDetailsSettingsRepository
import ru.fivefourtyfive.places.util.Preferences.PREFERENCE_SLIDESHOW
import javax.inject.Inject

class PlaceDetailsSettingsRepository @Inject constructor(private val settings: ISettingsDataSource) :
    IPlaceDetailsSettingsRepository {

    override fun enableSlideShow(enable: Boolean) =
        settings.putBoolean(PREFERENCE_SLIDESHOW, enable)

    override fun isSlideshowEnabled() = settings.getBoolean(PREFERENCE_SLIDESHOW)
}