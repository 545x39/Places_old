package ru.fivefourtyfive.wikimapper.util

import android.Manifest.permission.*

object Network {
    const val ROOT_URL = "https://wikimapia.org"
    const val API_URL = "http://api.wikimapia.org/"
    const val API_KEY = "F29DE311-79E27525-6803266D-365D72DD-2A145EEB-9C8F7110-26ACA826-139C9859"
    const val WIKIMEDIA_TILES_URL = "https://c.tiles.wmflabs.org/osm-no-labels/"
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
}