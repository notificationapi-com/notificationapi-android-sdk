package com.notificationapi.notificationapi_android_sdk.models

import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.RemoteMessage
import com.notificationapi.notificationapi_android_sdk.NotificationApi

class NotificationApiIntent(context: Context, handlerClass: Class<*>, message: RemoteMessage): Intent(context, handlerClass) {
    init {
        putExtra(NotificationApi.NOTIFICATION_INTENT_KEY, message)
        addCategory(CATEGORY_LAUNCHER)
        addFlags(FLAG_ACTIVITY_SINGLE_TOP)
        action = ACTION_MAIN
    }
}