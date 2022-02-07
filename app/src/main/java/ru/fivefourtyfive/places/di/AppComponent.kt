package ru.fivefourtyfive.places.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import ru.fivefourtyfive.places.Places
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocalDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.IRemoteDataSource
import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import ru.fivefourtyfive.places.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        ViewModelProviderFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: Places)

    fun getSettingsDataSource(): ISettingsDataSource

    fun getRemoteDataSource(): IRemoteDataSource

    fun getLocalDataSource(): ILocalDataSource

    fun getSharedPreferences() : SharedPreferences

    fun getContext(): Context

//    fun workerProviderFactory(): WorkerProviderFactory

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}