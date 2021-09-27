package ru.fivefourtyfive.map.presentation.util

import org.osmdroid.tileprovider.tilesource.TileSourcePolicy

const val MAP_LISTENER_DELAY = 600L

object Zoom {
    /** Smaller min values allows map to zoom out to show more than one map repetition.*/
    const val ZOOM_MIN = 2.0
    const val ZOOM_MAX = 19.0
    const val ZOOM_DEFAULT = 10.0
}

object MapMode {
    const val SCHEME = 0
    const val SATELLITE = 1
}

object TileSource {
    const val COPYRIGHT = "© OpenStreetMap contributors"
    const val TILE_PIXELS = 256
    const val TILE_EXTENSION = ".png"
    const val ARCGIS_MIN_ZOOM = 1
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
}