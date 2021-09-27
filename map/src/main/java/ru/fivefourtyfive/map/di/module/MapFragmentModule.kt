package ru.fivefourtyfive.map.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.di.MapFragmentScope

@Module
class MapFragmentModule {

    @MapFragmentScope
    @Provides
    fun provideMapView(context: Context) = MapView(context)

    @MapFragmentScope
    @Provides
    fun provideMyLocationProvider(context: Context) = GpsMyLocationProvider(context)

    @MapFragmentScope
    @Provides
    fun provideMyLocationOverlay(provider: GpsMyLocationProvider, mapView: MapView) =
        MyLocationNewOverlay(provider, mapView).apply {
            enableMyLocation()
            isEnabled = true
//            enableAutoStop = false
//            enableFollowLocation()
            isDrawAccuracyEnabled = true

        }
}