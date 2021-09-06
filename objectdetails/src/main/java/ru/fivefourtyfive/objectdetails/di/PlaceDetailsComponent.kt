package ru.fivefourtyfive.objectdetails.di

import dagger.Component
import ru.fivefourtyfive.objectdetails.presentation.ui.PlaceDetailsFragment
import ru.fivefourtyfive.wikimapper.di.AppComponent

@PlaceDetailsScope
@Component(dependencies = [AppComponent::class], modules = [PlaceDetailsModule::class])
interface PlaceDetailsComponent {

    fun inject(fragment: PlaceDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): PlaceDetailsComponent
    }

}