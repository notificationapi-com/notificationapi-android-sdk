package com.notificationapi.notificationapi_android_sdk.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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