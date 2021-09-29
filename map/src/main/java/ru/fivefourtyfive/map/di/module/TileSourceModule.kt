package ru.fivefourtyfive.map.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.overlay.TilesOverlay
import ru.fivefourtyfive.map.di.MapFragmentScope
import ru.fivefourtyfive.map.presentation.util.Overlay.TRANSPORTATION_OVERLAY
import ru.fivefourtyfive.map.presentation.util.Overlay.WIKIMAPIA_OVERLAY
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_LABELS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_MAX_ZOOM
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_MIN_ZOOM
import ru.fivefourtyfive.map.presentation.util.TileSource.ARCGIS_STREETS_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.COPYRIGHT
import ru.fivefourtyfive.map.presentation.util.TileSource.OSM_DEFAULT_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.POLICY
import ru.fivefourtyfive.map.presentation.util.TileSource.TILE_EXTENSION
import ru.fivefourtyfive.map.presentation.util.TileSource.TILE_SIZE_PIXELS
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMAPIA_TILE_SOURCE
import ru.fivefourtyfive.map.presentation.util.TileSource.WIKIMEDIA_NO_LABELS_TILE_SOURCE
import ru.fivefourtyfive.wikimapper.util.Network.ARCGIS_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMAPIA_TILE_SERVERS
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMEDIA_TILE_SERVERS
import javax.inject.Named

@Module
class TileSourceModule {

    @MapFragmentScope
    @Provides
    @Named(OSM_DEFAULT_TILE_SOURCE)
    fun provideDefaultTileSource(): OnlineTileSourceBase = TileSourceFactory.MAPNIK

    @MapFragmentScope
    @Provides
    @Named(WIKIMEDIA_NO_LABELS_TILE_SOURCE)
    fun provideWikimediaNoLabelsTileSource(): OnlineTileSourceBase = XYTileSource(
        WIKIMEDIA_NO_LABELS_TILE_SOURCE,
        0,
        19,
        TILE_SIZE_PIXELS,
        TILE_EXTENSION,
        WIKIMEDIA_TILE_SERVERS,
        COPYRIGHT,
        POLICY
    )

    @MapFragmentScope
    @Provides
    @Named(ARCGIS_IMAGERY_TILE_SOURCE)
    fun provideImageryTileSource(): OnlineTileSourceBase =
        object : TileSource(ARCGIS_IMAGERY_TILE_SOURCE) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/World_Imagery/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }

    @MapFragmentScope
    @Provides
    @Named(ARCGIS_STREETS_TILE_SOURCE)
    fun provideStreetsTileSource(): OnlineTileSourceBase =
        object : TileSource(ARCGIS_STREETS_TILE_SOURCE) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/World_Street_Map/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }

    @MapFragmentScope
    @Provides
    @Named(ARCGIS_IMAGERY_LABELS_TILE_SOURCE)
    fun provideImageryLabelsTileSource(): OnlineTileSourceBase =
        object : TileSource(ARCGIS_IMAGERY_LABELS_TILE_SOURCE) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/Reference/World_Boundaries_and_Places/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }

    @MapFragmentScope
    @Provides
    @Named(ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE)
    fun provideImageryTransportationTileSource(): OnlineTileSourceBase =
        object : TileSource(ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE) {
            override fun getTileURLString(pMapTileIndex: Long) =
                "$baseUrl/ArcGIS/rest/services/Reference/World_Transportation/MapServer/tile/${
                    MapTileIndex.getZoom(pMapTileIndex)
                }/${MapTileIndex.getY(pMapTileIndex)}/${MapTileIndex.getX(pMapTileIndex)}"
        }

    @MapFragmentScope
    @Provides
    @Named(WIKIMAPIA_TILE_SOURCE)
    fun provideWikimapiaTileSource(): OnlineTileSourceBase = object : TileSource(
        WIKIMAPIA_TILE_SOURCE,
        0,
        19,
        WIKIMAPIA_TILE_SERVERS
    ) {
        override fun getTileURLString(pMapTileIndex: Long) =
            "$baseUrl/?x=${MapTileIndex.getX(pMapTileIndex)}&y=${MapTileIndex.getY(pMapTileIndex)}&zoom=${
                MapTileIndex.getZoom(pMapTileIndex)
            }&type=hybrid&lng=1"
    }

    abstract class TileSource(
        name: String,
        minZoom: Int = ARCGIS_MIN_ZOOM,
        maxZoom: Int = ARCGIS_MAX_ZOOM,
        servers: Array<String> = ARCGIS_TILE_SERVERS
    ) : OnlineTileSourceBase(
        name,
        minZoom,
        maxZoom,
        TILE_SIZE_PIXELS,
        TILE_EXTENSION,
        servers,
        COPYRIGHT,
        POLICY
    )
}