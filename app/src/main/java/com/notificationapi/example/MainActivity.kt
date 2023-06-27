package com.notificationapi.example

import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
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

    // Handle clicked notifications
    override fun onNotificationClicked(message: RemoteMessage) {
        super.onNotificationClicked(message)

        Log.d(TAG, "Notification was clicked on")
    }

    // Handle notification permission request results
    override fun onNotificationRequestPermissionResult(isGranted: Boolean) {
        super.onNotificationRequestPermissionResult(isGranted)
        Log.d(TAG, "Notifications are authorized?: $isGranted")
    }
}