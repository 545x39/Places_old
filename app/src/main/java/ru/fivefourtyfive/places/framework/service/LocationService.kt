package ru.fivefourtyfive.places.framework.service


import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import ru.fivefourtyfive.places.Places
import ru.fivefourtyfive.places.data.datasource.abstraction.ILocationDataSource
import javax.inject.Inject

const val NOTIFICATION_ID = 100500
const val NOTIFICATION_CHANNEL_ID = "location_update_notification_channel"

class LocationService: Service()  {

    @Inject
    lateinit var locationDataSource: ILocationDataSource

    private val binder = LocationServiceBinder()

    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun onCreate() {
        super.onCreate()
        (application as Places).appComponent.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder = binder


    private inner class LocationServiceBinder : Binder() {
        val service: LocationService
            get() = this@LocationService
    }
}