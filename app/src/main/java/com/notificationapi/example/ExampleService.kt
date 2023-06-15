package com.notificationapi.example

import android.util.Log
import com.notificationapi.notificationapi_android_sdk.NotificationApiService

class ExampleService: NotificationApiService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("Example App", "New Token: $token")
    }
}