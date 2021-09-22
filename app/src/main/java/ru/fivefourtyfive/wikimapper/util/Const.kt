@file:Suppress("SpellCheckingInspection")

package ru.fivefourtyfive.wikimapper.util

import android.Manifest.permission.*

object Network {
    const val ROOT_URL = "https://wikimapia.org"
    const val API_URL = "http://api.wikimapia.org/"
    val WIKIMEDIA_TILE_SERVERS = arrayOf(
        "https://a.tiles.wmflabs.org/osm-no-labels/",
        "https://b.tiles.wmflabs.org/osm-no-labels/",
        "https://c.tiles.wmflabs.org/osm-no-labels/"
    )
    val WIKIMAPIA_TILE_SERVERS = arrayOf(
        "http://88.99.77.85",
        "http://88.99.77.89",
        "http://88.99.95.183",
        "http://88.99.95.187",
    )
    val GENERAL_HEADQUARTERS_TILE_SERVERS = arrayOf("http://88.99.52.155")
    val ARCGIS_TILE_SERVERS = arrayOf("http://server.arcgisonline.com/")
    const val CONNECT_TIMEOUT = 120L
    const val READ_TIMEOUT = 120L
}

object Permissions {
    val PERMISSIONS = arrayOf(
        READ_PHONE_STATE,
        WRITE_EXTERNAL_STORAGE,
        READ_EXTERNAL_STORAGE,
        ACCESS_FINE_LOCATION
    )
}

object Preferences {
    const val PREFERENCE_SLIDESHOW = "preference_slideshow"
    const val PREFERENCE_LAST_LOCATION = "preference_last_location"
    const val PREFERENCE_LAST_ZOOM = "preference_last_zoom"
    const val DEFAULT_LOCATION = "59.939039;30.315780"
}