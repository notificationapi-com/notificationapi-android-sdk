package com.notificationapi.notificationapi_android_sdk.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.utils.getRemoteMessage

open class NotificationApiActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationApi.initialize(context = this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NotificationApi.NOTIFICATION_PERMISSION_REQUEST) {
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            onNotificationRequestPermissionResult(isGranted)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.getRemoteMessage()?.let {
            intent.extras?.remove(NotificationApi.NOTIFICATION_INTENT_KEY)
            onNotificationClicked(it)
        }
    }

    open fun onNotificationClicked(message: RemoteMessage) { }

    open fun onNotificationRequestPermissionResult(isGranted: Boolean) { }
}