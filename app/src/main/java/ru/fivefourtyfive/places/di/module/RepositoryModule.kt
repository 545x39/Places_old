package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.repository.MapRepository
import ru.fivefourtyfive.places.data.repository.MapSettingsRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IMapRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IMapSettingsRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAreaRepository(repository: MapRepository): IMapRepository

    @Binds
    fun provideMapSettingsRepository(repository: MapSettingsRepository): IMapSettingsRepository
}