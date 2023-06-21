package com.notificationapi.notificationapi_android_sdk.repositories

import android.util.Log
import com.google.gson.Gson
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiDeviceInfo
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiError
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiTokenType
import com.notificationapi.notificationapi_android_sdk.utils.toBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class TokenRepository {
    companion object {
        private const val AGENT = "NotificationApi_Android_SDK"
        private const val VERSION = "1.0.0"
    }

    val http = OkHttpClient.Builder().addNetworkInterceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "$AGENT/$VERSION")
                .build()
        )
    }.build()

    private val authToken: String
        get() {
            val credentials = NotificationApi.shared.getCredentials()

            return "${credentials.clientId}:${credentials.userId}:${credentials.hashedUserId ?: "undefined"}".toBase64()
        }

    suspend fun syncToken(token: String) {
        return withContext(Dispatchers.IO) {
            val credentials = NotificationApi.shared.getCredentials()
            val baseUrl = try {
                NotificationApi.shared.config.baseUrl
            } catch (e: NotificationApiError) {
                Log.w(NotificationApi.TAG, e.message!!)
                return@withContext
            }

            var url = "$baseUrl/${credentials.clientId}/users/${credentials.userId}"

            credentials.hashedUserId?.let {
                url += "?hashedUserId=${it}"
            }

            val body = SyncApnTokenRequestBody(pushTokens = listOf(PushToken(type = NotificationApiTokenType.FCM, token = token, device = NotificationApi.shared.deviceInfo)))
            val jsonBody = Gson().toJson(body)

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "BASIC $authToken")
                .post(jsonBody.toRequestBody())
                .build()

            http.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw NotificationApiError.failedToSyncToken
                }
            }
        }
    }
}

data class SyncApnTokenRequestBody(val pushTokens: List<PushToken>)

data class PushToken(val type: NotificationApiTokenType, val token: String, val device: NotificationApiDeviceInfo)