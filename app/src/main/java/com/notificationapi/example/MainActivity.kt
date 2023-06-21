package com.notificationapi.example

import android.os.Bundle
import android.util.Log
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.activity.NotificationApiActivity
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiCredentials

class MainActivity : NotificationApiActivity() {
    companion object {
        const val TAG = "Example App"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure NotificationAPI with credentials
        NotificationApi.shared.configure(
            NotificationApiCredentials(
            clientId = "CLIENT_ID",
            userId = "USER_ID"
            )
        )

        // Request the user's permission to use notifications
        NotificationApi.shared.askNotificationPermissions()
    }

    override fun onNotificationRequestPermissionResult(isGranted: Boolean) {
        super.onNotificationRequestPermissionResult(isGranted)
        Log.d(TAG, "Notifications are authorized?: $isGranted")
    }
}