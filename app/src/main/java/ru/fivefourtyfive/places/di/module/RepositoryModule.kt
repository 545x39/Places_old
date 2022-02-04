package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.repository.AreaRepository
import ru.fivefourtyfive.places.data.repository.MapSettingsRepository
import ru.fivefourtyfive.places.data.repository.PlaceRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IAreaRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IMapSettingsRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IPlaceRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAreaRepository(repository: AreaRepository): IAreaRepository

    @Binds
    fun providePlaceRepository(repository: PlaceRepository): IPlaceRepository

    @Binds
    fun provideMapSettingsRepository(repository: MapSettingsRepository): IMapSettingsRepository
}