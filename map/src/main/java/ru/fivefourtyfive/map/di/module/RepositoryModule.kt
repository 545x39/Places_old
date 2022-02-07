package ru.fivefourtyfive.map.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.map.data.repository.MapSettingsRepository
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapRepository
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapSettingsRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAreaRepository(repository: ru.fivefourtyfive.map.data.repository.MapRepository): IMapRepository

    @Binds
    fun provideMapSettingsRepository(repository: MapSettingsRepository): IMapSettingsRepository
}