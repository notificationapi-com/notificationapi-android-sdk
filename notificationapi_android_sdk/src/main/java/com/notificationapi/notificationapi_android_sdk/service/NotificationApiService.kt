package com.notificationapi.notificationapi_android_sdk.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.R
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiError
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiIntent
import com.notificationapi.notificationapi_android_sdk.repositories.TokenRepository
import com.notificationapi.notificationapi_android_sdk.utils.getRemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class NotificationApiService: FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()

        NotificationApi.initialize(context = this)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                TokenRepository().syncToken(token)
            } catch (e: NotificationApiError) {
                Log.w(NotificationApi.TAG, e.message!!)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        onPreDisplayNotification(message)
    }

    fun displayNotification(intent: NotificationApiIntent, channelId: String = "default", channelName: String = "Notifications"): Int {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE)

        val message = intent.getRemoteMessage()

        val title = message?.data?.get("title") ?: message?.notification?.title ?: ""
        val body = message?.data?.get("body") ?: message?.notification?.body ?: ""

        val metadata = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData
        val defaultIcon = metadata.getInt("com.google.firebase.messaging.default_notification_icon")

        val icon = if (defaultIcon != 0) defaultIcon else R.drawable.ic_notificationapi

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(icon)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val uuid = System.currentTimeMillis().toInt()
        notificationManager.notify(uuid, notificationBuilder.build())

        return uuid
    }

    open fun onPreDisplayNotification(message: RemoteMessage) { }

}