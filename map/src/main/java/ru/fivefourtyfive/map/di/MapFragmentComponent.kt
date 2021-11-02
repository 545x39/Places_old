package ru.fivefourtyfive.map.di

import dagger.Component
import ru.fivefourtyfive.map.di.module.MapModule
import ru.fivefourtyfive.map.di.module.MapFragmentViewModelModule
import ru.fivefourtyfive.map.di.module.OverlayModule
import ru.fivefourtyfive.map.di.module.TileSourceModule
import ru.fivefourtyfive.map.presentation.ui.MapFragment
import ru.fivefourtyfive.wikimapper.di.AppComponent

@MapFragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        MapFragmentViewModelModule::class,
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