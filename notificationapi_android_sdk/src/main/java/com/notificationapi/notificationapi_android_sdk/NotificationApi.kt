package com.notificationapi.notificationapi_android_sdk

import android.annotation.SuppressLint
import android.content.Context
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiCredentials
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiDeviceInfo
import java.lang.Error

class NotificationApi private constructor(internal val context: Context) {
    internal lateinit var DEVICE_INFO: NotificationApiDeviceInfo

    companion object {
        internal const val TAG = "NotificationAPI"

        private const val CLIENT_ID = "notificationapi_client_id"
        private const val USER_ID = "notificationapi_user_id"
        private const val HASHED_ID = "notificationapi_hashed_user_id"

        @SuppressLint("StaticFieldLeak")
        private var _shared: NotificationApi? = null
        val shared: NotificationApi
            get() {
                _shared?.let { return it }
                throw Error("")
            }

        fun initialize(context: Context) {
            if (_shared == null) {
                _shared = NotificationApi(context)

                shared.DEVICE_INFO = NotificationApiDeviceInfo(context)
            }
        }
    }

    fun configure(credentials: NotificationApiCredentials) {
        val preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit()

        preferences.putString(CLIENT_ID, credentials.clientId)
        preferences.putString(USER_ID, credentials.userId)
        credentials.hashedUserId?.let { preferences.putString(HASHED_ID, it) }

        preferences.apply()
    }

    fun getCredentials(): NotificationApiCredentials {
        val preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)

        val clientId = preferences.getString(CLIENT_ID, "") as String
        val userId = preferences.getString(USER_ID, "") as String
        val hashedUserId = preferences.getString(HASHED_ID, null)

        return NotificationApiCredentials(
            clientId = clientId,
            userId = userId,
            hashedUserId = hashedUserId
        )
    }
}