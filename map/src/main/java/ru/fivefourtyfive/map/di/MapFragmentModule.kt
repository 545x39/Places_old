package ru.fivefourtyfive.map.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

@Module
class MapFragmentModule {

    @MapFragmentScope
    @Provides
    fun provideMyLocationProvider(context: Context) = GpsMyLocationProvider(context)
}