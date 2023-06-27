package com.notificationapi.example

import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiIntent
import com.notificationapi.notificationapi_android_sdk.service.NotificationApiService

class ExampleService: NotificationApiService() {

    // Handle FCM tokens whenever they are generated
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    // Build notifications before displaying them to the user
    override fun onPreDisplayNotification(message: RemoteMessage) {
        val onClickIntent = NotificationApiIntent(this, MainActivity::class.java, message)

        displayNotification(
            intent = onClickIntent
        )
    }
}