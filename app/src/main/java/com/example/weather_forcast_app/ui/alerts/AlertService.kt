package com.example.weather_forcast_app.ui.alerts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weather_forcast_app.MainActivity
import com.example.weather_forcast_app.R


private const val CHANNEL_ID = 1
private const val FOREGROUND_ID = 2

class AlertService : Service() {


    private var notificationManager: NotificationManager? = null
    var alertWindowManger: AlertManger? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val description = intent?.getStringExtra("description")
        notificationChannel()
        startForeground(FOREGROUND_ID, makeNotification(description!!))
        if (Settings.canDrawOverlays(this)) {
            alertWindowManger = AlertManger(this, description)
            alertWindowManger!!.initializeWindowManager()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun makeNotification(description: String): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return NotificationCompat.Builder(applicationContext, "$CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentText(description)
            .setContentTitle("Weather Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(description)
            )
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)
            .build()
    }

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: String = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("$CHANNEL_ID",name, importance)
            channel.enableVibration(true)
            channel.description = description
            notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}