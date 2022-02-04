package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.framework.datasource.remote.RetrofitDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.framework.datasource.local.RoomDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): IRemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): ILocalDataSource
}