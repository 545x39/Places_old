package ru.fivefourtyfive.map.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import ru.fivefourtyfive.map.di.MapFragmentScope
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.places.BuildConfig
import ru.fivefourtyfive.places.util.MapZoom
import timber.log.Timber
import java.io.File

@Module
class MapModule {

    @MapFragmentScope
    @Provides
    fun provideMapView(context: Context) = MapView(context).apply {
        Configuration.getInstance().apply {
            userAgentValue = BuildConfig.APPLICATION_ID
            osmdroidBasePath = File("${context.filesDir}")
            osmdroidTileCache = File(osmdroidBasePath, "cache")
            Timber.e("CACHE DIR: " + osmdroidTileCache.absolutePath)
        }
        setUseDataConnection(true)
        setMultiTouchControls(true)
        setScrollableAreaLimitLatitude(
            MapView.getTileSystem().maxLatitude,
            MapView.getTileSystem().minLatitude,
            0
        )
        setScrollableAreaLimitLongitude(
            MapView.getTileSystem().minLongitude,
            MapView.getTileSystem().maxLongitude,
            0
        )
        minZoomLevel = MapZoom.ZOOM_MIN
        maxZoomLevel = MapZoom.ZOOM_MAX
        isTilesScaledToDpi = true
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        controller.setZoom(MapZoom.ZOOM_DEFAULT)
        isVerticalMapRepetitionEnabled = true
        isHorizontalMapRepetitionEnabled = true
    }

    @MapFragmentScope
    @Provides
    fun provideLabels() = arrayListOf<IGeoPoint>()

    @MapFragmentScope
    @Provides
    fun providePlacePolygons() = arrayListOf<PlacePolygon>()

}