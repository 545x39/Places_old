package ru.fivefourtyfive.map.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.map.di.MapFragmentScope
import ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.map.domain.usecase.abstraction.factory.IMapUseCaseFactory
import ru.fivefourtyfive.map.domain.usecase.implementation.GetAreaUseCase
import ru.fivefourtyfive.map.domain.usecase.implementation.factory.MapUseCaseFactory

@Module
interface UseCaseModule {

    @MapFragmentScope
    @Binds
    fun provideUseCaseFactory(factory: MapUseCaseFactory): IMapUseCaseFactory

    @Binds
    fun provideGetAreaUseCase(useCase: GetAreaUseCase): IGetAreaUseCase
}