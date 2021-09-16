package ru.fivefourtyfive.wikimapper.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import ru.fivefourtyfive.wikimapper.Wikimapper
import ru.fivefourtyfive.wikimapper.data.repository.implementation.AreaRepository
import ru.fivefourtyfive.wikimapper.data.repository.implementation.PlaceRepository
import ru.fivefourtyfive.wikimapper.di.module.AppModule
import ru.fivefourtyfive.wikimapper.di.module.DataSourceModule
import ru.fivefourtyfive.wikimapper.di.module.NetworkModule
import ru.fivefourtyfive.wikimapper.di.module.ViewModelProviderFactoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DataSourceModule::class,
        ViewModelProviderFactoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: Wikimapper)

    fun getPreferences(): SharedPreferences

    fun getPlaceRepository(): PlaceRepository

    fun getAreaRepository(): AreaRepository

    fun getContext(): Context

//    fun workerProviderFactory(): WorkerProviderFactory

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}