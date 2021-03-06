package ru.fivefourtyfive.places.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import ru.fivefourtyfive.places.Places
import javax.inject.Singleton

@Module
class AppModule(private val application: Places) {

    @Singleton
    @Provides
    fun providePreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Singleton
    @Provides
    fun provideWorkManager() = WorkManager.getInstance(application)

    @Singleton
    @Provides
    fun provideContext(): Context = application.baseContext
}