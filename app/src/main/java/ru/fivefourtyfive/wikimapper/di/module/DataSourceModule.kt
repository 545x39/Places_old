package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.RetrofitDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.RemoteDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): RemoteDataSource

//    TODO
//    @Binds
//    fun provideLocalDataSource(dataSource: RoomDataSource): LocalDataSource
}