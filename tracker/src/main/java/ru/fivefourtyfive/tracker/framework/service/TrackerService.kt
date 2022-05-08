package ru.fivefourtyfive.tracker.framework.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import ru.fivefourtyfive.places.Places
import ru.fivefourtyfive.places.R
import ru.fivefourtyfive.places.domain.entity.tracks.Track
import ru.fivefourtyfive.places.framework.presentation.ui.MainActivity
import ru.fivefourtyfive.places.util.NotificationBuilder
import ru.fivefourtyfive.places.util.TRACKS_NOTIFICATION_CHANNEL_ID
import ru.fivefourtyfive.tracker.di.DaggerTrackerComponent
import ru.fivefourtyfive.tracker.di.TrackerComponent
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.factory.ITrackerUseCaseFactory
import javax.inject.Inject

const val NOTIFICATION_ID = 100500

class TrackerService : Service() {

    val job = SupervisorJob()

    private val component: TrackerComponent =
        DaggerTrackerComponent.factory().create((application as Places).appComponent)
            .also { it.inject(this) }

    @Inject
    lateinit var factory: ITrackerUseCaseFactory

    private lateinit var track: Track

    private val binder = LocationServiceBinder()

    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            factory.apply {
//                startLocationUpdates()
                track = getActiveTrackUseCase().execute()

            }
        }
        //TODO 1. Создать use case для инициализации трека (Если уже есть активный трек, использовать его, если нет - создать новый.)
        //TODO 2. Создать use case для остановки записи трека, но здесь его не вызывать на случай пересоздания сервиса.
        startForeground(NOTIFICATION_ID, notification())
        //TODO 3. Создать use case для дописывания свежеполученных координат в базу
        //TODO 4. Подписаться на данные из SharedFlow и вызывать там эти use case'ы.
        //TODO 5. Отписываться от SharedFlow в onDestroy.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        job.cancel()
//        locationSource.stopLocationUpdates()
        stopForeground(true)
        super.onDestroy()
    }

    private fun notification() = NotificationBuilder(
        baseContext,
        TRACKS_NOTIFICATION_CHANNEL_ID
    ).icon(R.drawable.ic_launcher_background)
        .ongoing(true)
        .title(resources.getString(R.string.track_is_being_recorded))
        .contentIntent(pendingIntent)
        .build()

    private val pendingIntent = PendingIntent.getActivity(
        baseContext, 0, Intent(
            baseContext,
            MainActivity::class.java
        ), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    private inner class LocationServiceBinder : Binder() {
        val service: TrackerService
            get() = this@TrackerService
    }
}