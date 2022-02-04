package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.framework.datasource.remote.RetrofitDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.framework.datasource.local.RoomDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): IRemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): ILocalDataSource
}