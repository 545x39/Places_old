package ru.fivefourtyfive.places.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.places.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.places.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.places.domain.usecase.implementation.GetAreaUseCase
import ru.fivefourtyfive.places.domain.usecase.implementation.GetPlaceUseCase
import ru.fivefourtyfive.places.domain.usecase.abstraction.factory.IUseCaseFactory
import ru.fivefourtyfive.places.domain.usecase.implementation.factory.UseCaseFactory

@Module
interface UseCaseModule {

    @Binds
    fun provideUseCaseFactory(factory: UseCaseFactory): IUseCaseFactory

    @Binds
    fun provideGetAreaUseCase(useCase: GetAreaUseCase): IGetAreaUseCase

    @Binds
    fun provideGetPlaceUseCase(useCase: GetPlaceUseCase): IGetPlaceUseCase
}