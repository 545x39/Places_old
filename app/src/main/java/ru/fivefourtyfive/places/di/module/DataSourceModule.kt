package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.RetrofitDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.local.RoomDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.location.LostLocationDataSource
import ru.fivefourtyfive.places.framework.datasource.implementation.settings.SharedPreferencesDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): IRemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): ILocalDataSource

    @Binds
    fun provideSettingsDataSource(dataSource: SharedPreferencesDataSource): ISettingsDataSource
}