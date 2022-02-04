package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.implementation.GetAreaUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.implementation.GetPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.factory.IUseCaseFactory
import ru.fivefourtyfive.wikimapper.domain.usecase.implementation.factory.UseCaseFactory

@Module
interface UseCaseModule {

    @Binds
    fun provideUseCaseFactory(factory: UseCaseFactory): IUseCaseFactory

    @Binds
    fun provideGetAreaUseCase(useCase: GetAreaUseCase): IGetAreaUseCase

    @Binds
    fun provideGetPlaceUseCase(useCase: GetPlaceUseCase): IGetPlaceUseCase
}