package ru.fivefourtyfive.map.di.module

import android.content.Context
import android.graphics.Color
import dagger.Module
import dagger.Provides
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fivefourtyfive.map.di.MapFragmentScope
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.Zoom
import ru.fivefourtyfive.wikimapper.BuildConfig
import ru.fivefourtyfive.wikimapper.util.ifTrue
import timber.log.Timber
import java.io.File

@Module
class MapFragmentModule {

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
            80
        )
        minZoomLevel = Zoom.ZOOM_MIN
        maxZoomLevel = Zoom.ZOOM_MAX
        isTilesScaledToDpi = true
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        controller.setZoom(Zoom.ZOOM_DEFAULT)
        isVerticalMapRepetitionEnabled = false
        isHorizontalMapRepetitionEnabled = false
    }

    @MapFragmentScope
    @Provides
    fun provideMyLocationProvider(context: Context) = GpsMyLocationProvider(context)

    @MapFragmentScope
    @Provides
    fun provideMyLocationOverlay(provider: GpsMyLocationProvider, mapView: MapView) =
        MyLocationNewOverlay(provider, mapView).apply {
            enableMyLocation()
        }

    @MapFragmentScope
    @Provides
    fun provideRotationGestureOverlay(mapView: MapView) = RotationGestureOverlay(mapView)

    @MapFragmentScope
    @Provides
    fun provideCompassOverlay(mapView: MapView) =
        CompassOverlay(
            mapView.context,
            InternalCompassOrientationProvider(mapView.context),
            mapView
        ).apply {enableCompass()}

    @MapFragmentScope
    @Provides
    fun provideGrid() = LatLonGridlineOverlay2()

    @MapFragmentScope
    @Provides
    fun provideScale(mapView: MapView) = ScaleBarOverlay(mapView).apply { setAlignBottom(true) }

    @MapFragmentScope
    @Provides
    fun provideFolderOverlay() = FolderOverlay()

    @MapFragmentScope
    @Provides
    fun provideLabels() = arrayListOf<IGeoPoint>()

    @MapFragmentScope
    @Provides
    fun providePlacePolygons() = arrayListOf<PlacePolygon>()

    @MapFragmentScope
    @Provides
    fun provideMapTileProvider(context : Context) = MapTileProviderBasic(context)

    @MapFragmentScope
    @Provides
    fun provideTileOverlay(provider: MapTileProviderBasic, context: Context) = getTileOverlay(provider, context)

    private fun getTileOverlay(provider: MapTileProviderBasic, context: Context) = TilesOverlay(provider, context).apply {
        loadingBackgroundColor = Color.TRANSPARENT
    }
}