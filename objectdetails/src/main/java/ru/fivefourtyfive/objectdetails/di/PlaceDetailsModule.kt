package ru.fivefourtyfive.objectdetails.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.fivefourtyfive.objectdetails.data.repository.PlaceDetailsRepository
import ru.fivefourtyfive.objectdetails.domain.repository.abstraction.IPlaceDetailsRepository
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
    fun providePlaceDetailsRepository(repository: PlaceDetailsRepository): IPlaceDetailsRepository

    @PlaceDetailsScope
    @Binds
    fun provideUseCaseFactory(factory: PlaceDetailsUseCaseFactory): IPlaceDetailsUseCaseFactory

}