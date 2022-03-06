package ru.fivefourtyfive.places.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocationDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.location.LostLocationDataSource

@Module
class LocationDataSourceModule {

    @Provides
    fun provideLocationDataSource(context: Context): ILocationDataSource =  LostLocationDataSource(context)

}