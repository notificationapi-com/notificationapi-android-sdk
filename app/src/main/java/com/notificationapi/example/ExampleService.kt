package com.notificationapi.example

import android.util.Log
import com.notificationapi.notificationapi_android_sdk.service.NotificationApiService

class ExampleService: NotificationApiService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(MainActivity.TAG, "New Token: $token")
    }
}