package ru.fivefourtyfive.objectdetails.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.wikimapper.di.ley.ViewModelKey

@Module
interface PlaceDetailsModule {

    @PlaceDetailsScope
    @Binds
    @IntoMap
    @ViewModelKey(PlaceDetailsViewModel::class)
    fun provideViewModel(viewModel: PlaceDetailsViewModel): ViewModel

}