package ru.fivefourtyfive.tracker.data.datasource.abstraction

import android.location.Location
import kotlinx.coroutines.flow.SharedFlow

interface ILocationDataSource {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    fun location(): SharedFlow<Location>
}