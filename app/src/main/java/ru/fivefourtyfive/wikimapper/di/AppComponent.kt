package ru.fivefourtyfive.wikimapper.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import ru.fivefourtyfive.wikimapper.Places
import ru.fivefourtyfive.wikimapper.di.module.*
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IAreaRepository
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IPlaceRepository
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.factory.IUseCaseFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        ViewModelProviderFactoryModule::class,
        UseCaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: Places)

    fun getPreferences(): SharedPreferences

    fun getPlaceRepository(): IPlaceRepository

    fun getAreaRepository(): IAreaRepository

    fun getUseCaseFactory(): IUseCaseFactory

    fun getContext(): Context

//    fun workerProviderFactory(): WorkerProviderFactory

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}