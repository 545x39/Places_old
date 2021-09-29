package ru.fivefourtyfive.map.di.module

import android.content.Context
import android.graphics.Color
import dagger.Module
import dagger.Provides
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
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
import ru.fivefourtyfive.map.presentation.util.Overlay
import ru.fivefourtyfive.map.presentation.util.Overlay.IMAGERY_LABELS_OVERLAY
import ru.fivefourtyfive.map.presentation.util.Overlay.TRANSPORTATION_OVERLAY
import ru.fivefourtyfive.map.presentation.util.Overlay.WIKIMAPIA_OVERLAY
import ru.fivefourtyfive.map.presentation.util.TileSource
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_LABELS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMAPIA_TILE_SOURCE
import javax.inject.Named

@Module
class OverlayModule {

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
        ).apply { enableCompass() }

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
    @Named(IMAGERY_LABELS_OVERLAY)
    fun provideImageryLabelsOverlay(
        context: Context,
        @Named(ARCGIS_IMAGERY_LABELS_TILE_SOURCE) tileSource: OnlineTileSourceBase
    ) = getTilesOverlay(context, tileSource)

    @MapFragmentScope
    @Provides
    @Named(TRANSPORTATION_OVERLAY)
    fun provideTransportationOverlay(
        context: Context,
        @Named(ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE) tileSource: OnlineTileSourceBase
    ) = getTilesOverlay(context, tileSource)

    @MapFragmentScope
    @Provides
    @Named(WIKIMAPIA_OVERLAY)
    fun provideWikimapiaOverlay(
        context: Context,
        @Named(WIKIMAPIA_TILE_SOURCE)
        tileSource: OnlineTileSourceBase
    ) = getTilesOverlay(context, tileSource)

    private fun getTilesOverlay(
        context: Context,
        source: OnlineTileSourceBase? = null
    ) = TilesOverlay(
        MapTileProviderBasic(context).apply { source?.let { tileSource = it } },
        context
    ).apply { loadingBackgroundColor = Color.TRANSPARENT }
}