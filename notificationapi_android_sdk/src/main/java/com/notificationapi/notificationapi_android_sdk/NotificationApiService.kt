package com.notificationapi.notificationapi_android_sdk

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

open class NotificationApiService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("NAPI", "FCM Token: $token")
    }
}