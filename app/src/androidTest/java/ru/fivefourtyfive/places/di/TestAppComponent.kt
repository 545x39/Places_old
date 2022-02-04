package ru.fivefourtyfive.places.di

import dagger.Component
import ru.fivefourtyfive.places.framework.datasource.remote.RemoteDataSourceTests
import ru.fivefourtyfive.places.di.module.AppModule
import ru.fivefourtyfive.places.di.module.DataSourceModule
import ru.fivefourtyfive.places.di.module.NetworkModule
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