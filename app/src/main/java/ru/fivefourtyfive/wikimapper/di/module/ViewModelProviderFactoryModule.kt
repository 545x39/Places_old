package ru.fivefourtyfive.wikimapper.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import javax.inject.Singleton

@Module
interface ViewModelProviderFactoryModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}