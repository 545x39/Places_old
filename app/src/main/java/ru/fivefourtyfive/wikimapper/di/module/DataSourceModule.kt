package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.data.datasource.remote.RetrofitDataSource
import ru.fivefourtyfive.wikimapper.data.repository.abstraction.RemoteDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): RemoteDataSource

//    TODO
//    @Binds
//    fun provideRemoteDataSource(dataSource: RoomDataSource): LocalDataSource
}