package com.avivba.simbanyu.data.remote.service

import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import com.google.firebase.messaging.FirebaseMessagingService
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

        serviceScope.launch {
            if (authRepository.isLoggedIn()) {
                fcmTokenRepository.save(token)
            }
        }
    }
}