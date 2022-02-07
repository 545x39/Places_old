package ru.fivefourtyfive.map.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.fivefourtyfive.map.di.MapFragmentScope
import ru.fivefourtyfive.map.presentation.viewmodel.MapFragmentViewModel
import ru.fivefourtyfive.places.di.key.ViewModelKey

@Module
interface MapFragmentViewModelModule {

    @MapFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    fun provideViewModel(viewModel: MapFragmentViewModel): ViewModel

}