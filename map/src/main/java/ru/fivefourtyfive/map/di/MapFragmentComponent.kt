package ru.fivefourtyfive.map.di

import dagger.Component
import ru.fivefourtyfive.map.di.module.*
import ru.fivefourtyfive.map.presentation.ui.MapFragment
import ru.fivefourtyfive.places.di.AppComponent

@MapFragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MapFragmentViewModelModule::class,
        RepositoryModule::class,
        MapModule::class,
        TileSourceModule::class,
        OverlayModule::class
    ]
)
interface MapFragmentComponent {

    fun inject(fragment: MapFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MapFragmentComponent
    }
}