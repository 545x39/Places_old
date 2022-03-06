package ru.fivefourtyfive.places.framework.datasource.implementation.location

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocationDataSource

class LocationService: Service(), ILocationDataSource  {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun startLocationUpdates() {

    }

    override fun stopLocationUpdates() {

    }

    override fun getLastKnownLocation(): Location {
        return Location("")
    }
}