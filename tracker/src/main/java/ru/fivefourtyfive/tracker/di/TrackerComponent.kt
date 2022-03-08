package ru.fivefourtyfive.tracker.di

import dagger.Component
import ru.fivefourtyfive.places.di.AppComponent
import ru.fivefourtyfive.tracker.di.module.DataSourceModule
import ru.fivefourtyfive.tracker.di.module.TrackerModule
import ru.fivefourtyfive.tracker.framework.service.TrackerService

@TrackerScope
@Component(
    dependencies = [AppComponent::class],
    modules = [TrackerModule::class, DataSourceModule::class]
)
interface TrackerComponent {

    fun inject(service: TrackerService)

//    fun getLocalDataSource(): ILocalDataSource

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): TrackerComponent
    }

}