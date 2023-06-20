package com.notificationapi.notificationapi_android_sdk.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.repositories.TokenRepository
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

        Log.d(NotificationApi.TAG, "FCM Token: $token")

        CoroutineScope(Dispatchers.IO).launch {
            TokenRepository().syncToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(NotificationApi.TAG, "Got message: ${message.notification?.title}")
    }
}