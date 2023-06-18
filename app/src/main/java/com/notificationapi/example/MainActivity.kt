package com.notificationapi.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notificationapi.notificationapi_android_sdk.NotificationApi
import com.notificationapi.notificationapi_android_sdk.models.NotificationApiCredentials

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationApi.initialize(context = this)

        NotificationApi.shared.configure(
            NotificationApiCredentials(
            clientId = "2vgja235ahrfqc4n8hjfol06ur",
            userId = "androidTest"
            )
        )
    }
}