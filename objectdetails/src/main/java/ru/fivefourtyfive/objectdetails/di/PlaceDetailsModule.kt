package ru.fivefourtyfive.objectdetails.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.fivefourtyfive.objectdetails.data.repository.PlaceRepository
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceRepository
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.objectdetails.domain.usecase.abstraction.factory.IPlaceDetailsUseCaseFactory
import ru.fivefourtyfive.objectdetails.domain.usecase.implementation.GetPlaceUseCase
import ru.fivefourtyfive.objectdetails.domain.usecase.implementation.PlaceDetailsUseCaseFactory
import ru.fivefourtyfive.objectdetails.presentation.viewmodel.PlaceDetailsViewModel
import ru.fivefourtyfive.places.di.key.ViewModelKey

@Module
interface PlaceDetailsModule {

    @PlaceDetailsScope
    @Binds
    @IntoMap
    @ViewModelKey(PlaceDetailsViewModel::class)
    fun provideViewModel(viewModel: PlaceDetailsViewModel): ViewModel

    @Binds
    fun provideGetPlaceUseCase(useCase: GetPlaceUseCase): IGetPlaceUseCase

    @PlaceDetailsScope
    @Binds
    fun providePlaceRepository(repository: PlaceRepository): IPlaceRepository

    @PlaceDetailsScope
    @Binds
    fun provideUseCaseFactory(factory: PlaceDetailsUseCaseFactory): IPlaceDetailsUseCaseFactory

}