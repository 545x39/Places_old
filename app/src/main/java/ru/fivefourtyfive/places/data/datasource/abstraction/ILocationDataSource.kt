package ru.fivefourtyfive.places.data.datasource.abstraction

import android.location.Location

interface ILocationDataSource {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    fun getLastKnownLocation(): Location
}