package com.notificationapi.notificationapi_android_sdk.repositories

import android.util.Log
import com.google.gson.Gson
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiDeviceInfo
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiTokenType
import com.notificationapi.notificationapi_android_sdk.toBase64
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class TokenRepository {
    companion object {
        const val BASE_URL = "https://api.notificationapi.com"
    }

    val http = OkHttpClient.Builder().addNetworkInterceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
        )
    }.build()

    private val authToken: String
        get() {
            val credentials = NotificationApi.shared.getCredentials()

            return "${credentials.clientId}:${credentials.userId}:${credentials.hashedUserId ?: "undefined"}".toBase64()
        }

    fun syncToken(token: String) {
        val credentials = NotificationApi.shared.getCredentials()

        var url = "$BASE_URL/${credentials.clientId}/users/${credentials.userId}"

        credentials.hashedUserId?.let {
            url += "?hashedUserId=${it}"
        }

        val body = SyncApnTokenRequestBody(pushTokens = listOf(PushToken(type = NotificationApiTokenType.FCM, token = token, device = NotificationApi.shared.DEVICE_INFO)))
        val jsonBody = Gson().toJson(body)

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "BASIC $authToken")
            .post(jsonBody.toRequestBody())
            .build()

        http.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.d(NotificationApi.TAG, "$response")
            }

            Log.d(NotificationApi.TAG, response.body!!.string())
        }
    }
}

data class SyncApnTokenRequestBody(val pushTokens: List<PushToken>)

data class PushToken(val type: NotificationApiTokenType, val token: String, val device: NotificationApiDeviceInfo)