package com.notificationapi.notificationapi_android_sdk

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiConfig
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiCredentials
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiDeviceInfo
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiError

class NotificationApi private constructor(private val context: Context) {
    internal lateinit var deviceInfo: NotificationApiDeviceInfo

    private lateinit var mConfig: NotificationApiConfig
    internal val config: NotificationApiConfig
        get() {
            if (this::mConfig.isInitialized) {
                return mConfig
            }
            throw NotificationApiError.missingCredentials
        }

    companion object {
        internal const val TAG = "NotificationAPI"

        const val NOTIFICATION_PERMISSION_REQUEST = 0
        const val NOTIFICATION_INTENT_KEY = "notificationapi_notification_intent_key"

        private const val CLIENT_ID = "notificationapi_client_id"
        private const val USER_ID = "notificationapi_user_id"
        private const val HASHED_ID = "notificationapi_hashed_user_id"

        @SuppressLint("StaticFieldLeak")
        private var mShared: NotificationApi? = null
        val shared: NotificationApi
            get() {
                mShared?.let { return it }
                throw NotificationApiError.uninitialized
            }

        fun initialize(context: Context) {
            if (mShared == null) {
                mShared = NotificationApi(context)

                shared.deviceInfo = NotificationApiDeviceInfo(context)
            }
        }
    }

    fun configure(credentials: NotificationApiCredentials, config: NotificationApiConfig = NotificationApiConfig("https://api.notificationapi.com")) {
        this.mConfig = config
        val preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit()

        preferences.putString(CLIENT_ID, credentials.clientId)
        preferences.putString(USER_ID, credentials.userId)
        credentials.hashedUserId?.let { preferences.putString(HASHED_ID, it) }

        preferences.apply()
    }

    fun askNotificationPermissions(requestCode: Int = NOTIFICATION_PERMISSION_REQUEST) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                if (context is Activity) {
                    context.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), requestCode)
                }
            }
        }
    }

    internal fun getCredentials(): NotificationApiCredentials {
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