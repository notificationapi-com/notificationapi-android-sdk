package com.notificationapi.notificationapi_android_sdk.utils

import android.util.Base64

fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}