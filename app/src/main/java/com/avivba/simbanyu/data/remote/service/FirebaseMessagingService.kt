package com.avivba.simbanyu.data.remote.service

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.avivba.simbanyu.R
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SimbanyuFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    private val fcmTokenRepository: FcmTokenRepository by inject()
    private val authRepository: AuthRepository by inject()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("SDKSDK", "New token received: $token")

        serviceScope.launch {
            if (authRepository.isLoggedIn()) {
                fcmTokenRepository.save(token)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            Log.d("SDKSDK", "Message Received: ${it.title} | ${it.body}")
            showNotification(it.title ?: "", it.body ?: "")
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(this, "simbanyu_channel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.logo_only)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}