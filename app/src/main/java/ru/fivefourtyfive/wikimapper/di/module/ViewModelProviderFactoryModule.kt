package ru.fivefourtyfive.wikimapper.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.wikimapper.di.factory.ViewModelProviderFactory
import javax.inject.Singleton

@Module
abstract class ViewModelProviderFactoryModule {

    @Singleton
    @Binds
    /** Возвращать нужно именно родительский тип, иначе будет "found dependency cycle."*/
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}