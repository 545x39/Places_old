package ru.fivefourtyfive.map.presentation.util

import org.osmdroid.tileprovider.tilesource.TileSourcePolicy

object MapListenerDelay{
    const val DEFAULT_DELAY = 600L
    const val FOLLOWING_LOCATION_DELAY = 1500L
}

/** Maximum zoom level for this source is 17, in case of greater number it sends
 * gray tiles with "No tile data available" text on them.*/
@Suppress("SpellCheckingInspection")
object TileSource {
    const val COPYRIGHT = "© OpenStreetMap contributors"
    const val TILE_SIZE_PIXELS = 256
    const val TILE_EXTENSION = ".png"
    const val ARCGIS_MIN_ZOOM = 2
    const val ARCGIS_MAX_ZOOM = 17
    val POLICY = TileSourcePolicy(
        2,
        TileSourcePolicy.FLAG_NO_BULK
                or TileSourcePolicy.FLAG_NO_PREVENTIVE
//                or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
//                or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
    )
    const val OSM_DEFAULT_TILE_SOURCE = "Mapnik"
    const val WIKIMEDIA_NO_LABELS_TILE_SOURCE = "WikimediaNoLabels"
    const val ARCGIS_STREETS_TILE_SOURCE = "ArcGISStreets"
    const val ARCGIS_IMAGERY_TILE_SOURCE = "ArcGISImagery"
    const val ARCGIS_IMAGERY_LABELS_TILE_SOURCE = "ArcGISImageryLabels"
    const val ARCGIS_IMAGERY_TRANSPORTATION_TILE_SOURCE = "ArcGISImageryTransportation"
    const val WIKIMAPIA_TILE_SOURCE = "Wikimapia"
    const val CARTO_DARK_TILE_SOURCE = "CartoDark"
    const val CARTO_LIGHT_TILE_SOURCE = "CartoLight"
    const val CARTO_VOYAGER_TILE_SOURCE = "CartoVoyager"
}

object Overlay{
    const val WIKIMAPIA_OVERLAY = "WikimapiaOverlay"
    const val IMAGERY_LABELS_OVERLAY = "ImageryLabelsOverlay"
    const val TRANSPORTATION_OVERLAY = "TransportationOverlay"
}