package com.notificationapi.notificationapi_android_sdk.utils

import android.content.Intent
import android.util.Base64
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.NotificationApi

fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}

fun Intent.getRemoteMessage(): RemoteMessage? {
    (this.extras?.get(NotificationApi.NOTIFICATION_INTENT_KEY) as? RemoteMessage)?.let { message -> return message }

    return null
}