package ru.fivefourtyfive.map.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.wikimapper.di.key.ViewModelKey

@Module
interface MapFragmentModule {

    @MapFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    fun provideViewModel(viewModel: MapFragmentViewModel): ViewModel
}