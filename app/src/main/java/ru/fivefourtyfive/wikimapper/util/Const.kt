package ru.fivefourtyfive.wikimapper.util

import android.Manifest.permission.*

object Network {
    const val BASE_URL = "http://api.wikimapia.org/"
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