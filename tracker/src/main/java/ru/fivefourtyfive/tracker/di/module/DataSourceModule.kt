package ru.fivefourtyfive.tracker.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.fivefourtyfive.tracker.data.datasource.abstraction.ILocationDataSource
import ru.fivefourtyfive.tracker.di.TrackerScope
import ru.fivefourtyfive.tracker.framework.datasource.implementation.LostLocationDataSource

@Module
class DataSourceModule {

    @TrackerScope
    @Provides
    fun provideLocationDataSource(context: Context): ILocationDataSource =
        LostLocationDataSource(context)

}