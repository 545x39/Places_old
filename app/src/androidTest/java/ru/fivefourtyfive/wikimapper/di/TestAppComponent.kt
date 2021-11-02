package ru.fivefourtyfive.wikimapper.di

import dagger.Component
import ru.fivefourtyfive.wikimapper.data.datasource.remote.RemoteDataSourceTests
import ru.fivefourtyfive.wikimapper.di.module.AppModule
import ru.fivefourtyfive.wikimapper.di.module.DataSourceModule
import ru.fivefourtyfive.wikimapper.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DataSourceModule::class
    ]
)
interface TestAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): TestAppComponent
    }

    fun inject(remoteDataSourceTests: RemoteDataSourceTests)
}