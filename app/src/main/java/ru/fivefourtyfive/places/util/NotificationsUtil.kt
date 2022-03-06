package ru.fivefourtyfive.places.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.fivefourtyfive.places.R

const val TRACKS_NOTIFICATION_CHANNEL_ID = "TRACKS_NOTIFICATION_CHANNEL_ID"

fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        listOf(tracksChannel(context))//Probably there will be more channels later
            .forEach {
                manager.createNotificationChannel(
                it
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun tracksChannel(context: Context) = NotificationChannelBuilder(
    TRACKS_NOTIFICATION_CHANNEL_ID,
    context.resources.getString(R.string.tracks_notification_channel_name)
).badge(true).vibration(true).lights(true).build()


@RequiresApi(Build.VERSION_CODES.O)
class NotificationChannelBuilder(channelId: String, channelName: String) {

    private val channel = NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT)

    fun description(description: String = "") = this.apply { channel.description = description }

    fun lights(enable: Boolean = false) = this.apply { channel.enableLights(enable) }

    fun vibration(enable: Boolean = false) = this.apply { channel.enableVibration(enable) }

    fun badge(enable: Boolean = false) = this.apply { channel.setShowBadge(enable) }

    fun importance(importance: Int = IMPORTANCE_DEFAULT) =
        this.apply { channel.importance = importance }

    fun build() = channel
}

class NotificationBuilder(context: Context, channelId: String) {

    private val builder = NotificationCompat.Builder(context, channelId)

    fun icon(resId: Int) = this.apply { builder.setSmallIcon(resId) }

    fun title(title: String) = this.apply { builder.setContentTitle(title) }

    fun ongoing(ongoing: Boolean = false) = this.apply { builder.setOngoing(ongoing) }

    fun priority(priority: Int = NotificationCompat.PRIORITY_DEFAULT) =
        this.apply { builder.priority = priority }

    fun contentIntent(intent: PendingIntent? = null) =
        this.apply { builder.setContentIntent(intent) }

    fun build() = builder.build()
}
