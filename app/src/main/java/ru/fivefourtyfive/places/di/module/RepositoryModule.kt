package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.repository.AreaRepository
import ru.fivefourtyfive.places.data.repository.MapSettingsRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IAreaRepository
import ru.fivefourtyfive.places.domain.repository.abstraction.IMapSettingsRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAreaRepository(repository: AreaRepository): IAreaRepository

    @Binds
    fun provideMapSettingsRepository(repository: MapSettingsRepository): IMapSettingsRepository
}