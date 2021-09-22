package ru.fivefourtyfive.map.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import ru.fivefourtyfive.map.di.MapFragmentScope

@Module
class MapFragmentModule {

    @MapFragmentScope
    @Provides
    fun provideMyLocationProvider(context: Context) = GpsMyLocationProvider(context)
}