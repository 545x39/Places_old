package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.LocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.RetrofitDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.RoomDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): RemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): LocalDataSource
}