package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IPersistPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.PersistPlaceUseCase
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory.IUseCaseFactory

@Module
interface UseCaseModule {

    @Binds
    fun providePersistUseCase(useCase: PersistPlaceUseCase): IPersistPlaceUseCase

//    @Binds
//    fun provideUseCaseFactory(factory: UseCaseFactory): IUseCaseFactory
}