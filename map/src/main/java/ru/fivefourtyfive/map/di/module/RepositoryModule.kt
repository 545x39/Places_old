package ru.fivefourtyfive.map.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.repository.MapSettingsRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IMapSettingsRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideMapSettingsRepository(repository: MapSettingsRepository): IMapSettingsRepository
}