package com.notificationapi.notificationapi_android_sdk.models

import android.content.Context

class NotificationApiDeviceInfo internal constructor(context: Context) {
    val app_id: String
    val ad_id: String
    val device_id: String
    val platform: String
    val manufactuer: String
    val model: String

    init {
        app_id = context.packageName
        ad_id = ""
        device_id = ""
        platform = "Android"
        manufactuer = android.os.Build.MANUFACTURER
        model = android.os.Build.MODEL
    }
}