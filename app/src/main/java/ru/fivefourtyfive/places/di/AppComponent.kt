package ru.fivefourtyfive.places.di

import android.content.Context
import dagger.Component
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import ru.fivefourtyfive.places.di.module.*
import ru.fivefourtyfive.places.framework.service.LocationService
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        LocationDataSourceModule::class,
        ViewModelProviderFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: Context)

    fun inject(service: LocationService)

    fun getSettingsDataSource(): ISettingsDataSource

    fun getRemoteDataSource(): IRemoteDataSource

    fun getLocalDataSource(): ILocalDataSource

    fun getContext(): Context

//    fun workerProviderFactory(): WorkerProviderFactory

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}