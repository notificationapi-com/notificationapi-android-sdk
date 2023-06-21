package com.notificationapi.notificationapi_android_sdk.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiError
import com.notificationapi.notificationapi_android_sdk.repositories.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class NotificationApiService: FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()

        NotificationApi.initialize(context = this)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                TokenRepository().syncToken(token)
            } catch (e: NotificationApiError) {
                Log.w(NotificationApi.TAG, e.message!!)
            }
        }
    }
}