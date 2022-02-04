package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.data.repository.AreaRepository
import ru.fivefourtyfive.wikimapper.data.repository.PlaceRepository
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IAreaRepository
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IPlaceRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAreaRepository(repository: AreaRepository): IAreaRepository

    @Binds
    fun providePlaceRepository(repository: PlaceRepository): IPlaceRepository
}