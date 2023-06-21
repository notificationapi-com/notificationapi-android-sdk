package com.notificationapi.notificationapi_android_sdk.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notificationapi.notificationapi_android_sdk.NotificationApi

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
        if (requestCode == 0) {
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            onNotificationRequestPermissionResult(isGranted)
        }
    }

    open fun onNotificationRequestPermissionResult(isGranted: Boolean) { }
}