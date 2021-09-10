package ru.fivefourtyfive.map.di

import dagger.Component
import ru.fivefourtyfive.map.presentation.ui.MapFragment
import ru.fivefourtyfive.wikimapper.di.AppComponent

@MapFragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MapFragmentModule::class]
)
interface MapFragmentComponent {

    fun inject(fragment: MapFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MapFragmentComponent
    }
}