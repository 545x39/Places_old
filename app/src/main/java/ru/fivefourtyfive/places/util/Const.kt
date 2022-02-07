@file:Suppress("SpellCheckingInspection")

package ru.fivefourtyfive.places.util

import android.Manifest.permission.*

object Network {
    const val ROOT_URL                      = "https://wikimapia.org"
    const val API_URL                       = "http://api.wikimapia.org/"
    const val WIKIMAPIA_POLYGON_PATH        = "/z1/itiles/"

    val WIKIMEDIA_TILE_SERVERS              = arrayOf(
                                               "https://a.tiles.wmflabs.org/osm-no-labels/",
                                               "https://b.tiles.wmflabs.org/osm-no-labels/",
                                               "https://c.tiles.wmflabs.org/osm-no-labels/"
                                            )
    val WIKIMAPIA_TILE_SERVERS              = arrayOf(
                                                "http://88.99.77.85",
                                                "http://88.99.77.89",
                                                "http://88.99.95.183",
                                                "http://88.99.95.187",
                                            )

    val ARCGIS_TILE_SERVERS                 = arrayOf(
                                                "http://server.arcgisonline.com/",
                                                "http://services.arcgisonline.com/"
                                            )

    const val CONNECT_TIMEOUT               = 120L
    const val READ_TIMEOUT                  = 120L
}

object Permissions {
    val PERMISSIONS                         = arrayOf(
                                              READ_PHONE_STATE,
                                              WRITE_EXTERNAL_STORAGE,
                                              READ_EXTERNAL_STORAGE,
                                              ACCESS_FINE_LOCATION,
                                              ACCESS_COARSE_LOCATION
                                            )
}

object Preferences {
    const val PREFERENCE_SLIDESHOW              = "preference_slideshow"
    const val PREFERENCE_LAST_LOCATION          = "preference_last_location"
    const val PREFERENCE_LAST_ZOOM              = "preference_last_zoom"
    const val PREFERENCE_MAP_MODE               = "preference_map_mode"
    const val PREFERENCE_TRANSPOTRATION_OVERLAY = "preference_transportation_overlay"
    const val PREFERENCE_WIKIMAPIA_OVERLAYS     = "preference_wikimapia_overlays"
    const val PREFERENCE_FOLLOW_LOCATION        = "preference_follow_location"
    const val PREFERENCE_CENTER_SELECTION       = "preference_center_selection"
    const val PREFERENCE_SHOW_SCALE             = "preference_show_scale"
    const val PREFERENCE_SHOW_GRID              = "preference_show_grid"
    const val PREFERENCE_KEEP_SCREEN_ON         = "preference_keep_screen_on"
    const val PREFERENCE_AUTO_ROTATE_MAP        = "preference_auto_rotate_map"
    ////
    const val DEFAULT_LOCATION                  = "59.939039;30.315780"
}

object MapZoom {
    /** Smaller min values allows map to zoom out to show more than one map repetition.*/
    const val ZOOM_MIN = 2.0
    const val ZOOM_MAX = 18.0
    const val ZOOM_DEFAULT = 13.0
}

object MapMode {
    const val SCHEME = 0
    const val SATELLITE = 1
}
