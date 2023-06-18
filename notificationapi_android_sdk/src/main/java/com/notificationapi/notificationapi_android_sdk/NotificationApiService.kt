package com.notificationapi.notificationapi_android_sdk

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.repositories.TokenRepository

open class NotificationApiService: FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()

        NotificationApi.initialize(context = this)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(NotificationApi.TAG, "FCM Token: $token")

        TokenRepository().syncToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(NotificationApi.TAG, "Got message: ${message.notification?.title}")
    }
}