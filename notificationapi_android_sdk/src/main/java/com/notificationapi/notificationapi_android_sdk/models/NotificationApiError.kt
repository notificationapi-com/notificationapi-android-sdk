package com.notificationapi.notificationapi_android_sdk.models

import java.lang.Exception

class NotificationApiError(message: String): Exception(message) {
    companion object {
        val unintialized = NotificationApiError("NotificationApi has not been initialized. Did you forget to call NotificationApi.initialize()?")
        val missingCredentials = NotificationApiError("NotificationApi has not been configured with any credentials. Did you forget to call NotificationApi.configure()?")
        val failedToSyncToken = NotificationApiError("Failed to sync device token with NotificationApi")
    }
}