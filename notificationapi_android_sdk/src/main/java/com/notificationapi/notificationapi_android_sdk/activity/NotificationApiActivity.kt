package com.notificationapi.notificationapi_android_sdk.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notificationapi.notificationapi_android_sdk.NotificationApi

open class NotificationApiActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationApi.initialize(context = this)
    }
}