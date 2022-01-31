package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.remote.RetrofitDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.IRemoteDataSource
import ru.fivefourtyfive.wikimapper.data.datasource.local.RoomDataSource

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: RetrofitDataSource): IRemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): ILocalDataSource
}