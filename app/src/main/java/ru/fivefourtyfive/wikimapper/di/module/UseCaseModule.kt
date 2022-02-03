package ru.fivefourtyfive.wikimapper.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory.IUseCaseFactory
import ru.fivefourtyfive.wikimapper.domain.interactor.implementation.factory.UseCaseFactory

@Module
interface UseCaseModule {

    @Binds
    fun provideUseCaseFactory(factory: UseCaseFactory): IUseCaseFactory
}