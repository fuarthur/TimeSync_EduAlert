package com.ams.timesyncedualert.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.ams.timesyncedualert.R

object NotificationHelper {
    private const val CHANNEL_ID = "timeSync_eduAlert"

    fun sendNotification(context: Context, title: String, content: String) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        val channelName = "TimeSync EduAlert"
        val channelDescription = "TimeSync EduAlert Notification"
        val channelImportance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
