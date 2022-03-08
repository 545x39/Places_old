package ru.fivefourtyfive.tracker.framework.datasource.implementation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.mapzen.android.lost.api.LocationListener
import com.mapzen.android.lost.api.LocationRequest
import com.mapzen.android.lost.api.LocationServices
import com.mapzen.android.lost.api.LostApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.fivefourtyfive.places.util.PermissionsUtil.arePermissionsGranted
import ru.fivefourtyfive.places.util.ifTrue
import ru.fivefourtyfive.tracker.data.datasource.abstraction.ILocationDataSource
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//<editor-fold defaultstate="collapsed" desc="CONSTANTS">
const val FASTEST_INTERVAL = 1L
const val INTERVAL = 5L
const val SMALLEST_DISPLACEMENT = 5.0f
//</editor-fold>

class LostLocationDataSource @Inject constructor(private val context: Context) :
    ILocationDataSource {

    //<editor-fold defaultstate="collapsed" desc="PRIVATE FIELDS">
    private val _locations = MutableSharedFlow<Location>()

    private val callbacks = ConnectionCallbacks()

    private val locationClient: LostApiClient = LostApiClient.Builder(context)
        .addConnectionCallbacks(callbacks).build()

    private val locationRequestSettings = LocationRequest.create().apply {
        fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_INTERVAL)
        interval = TimeUnit.SECONDS.toMillis(INTERVAL)
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = SMALLEST_DISPLACEMENT
    }

    private val locationListener = LocationListener {
        CoroutineScope(IO).launch {
            _locations.emit(it)
            Timber.e("LAT: [${it.latitude}], LON: [${it.longitude}]")
        }
    }
    //</editor-fold>

    override fun startLocationUpdates() {
        permissionsGranted().ifTrue { locationClient.connect() }
    }

    override fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(locationClient, locationListener)
        locationClient.disconnect()
    }

    override fun location() = _locations.asSharedFlow()

    private fun permissionsGranted() = arePermissionsGranted(
        context, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    )

    //<editor-fold defaultstate="collapsed" desc="INNER CLASS">
    inner class ConnectionCallbacks : LostApiClient.ConnectionCallbacks {
        @SuppressLint("MissingPermission")
        override fun onConnected() {
            if (permissionsGranted()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    locationClient,
                    locationRequestSettings,
                    locationListener
                )
            }
        }

        override fun onConnectionSuspended() {}
    }
    //</editor-fold>
}