package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.FcmTokenService
import com.avivba.simbanyu.data.remote.model.fcmtoken.FcmTokenRequest
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FcmTokenRepositoryImpl(
    private val fcmTokenService: FcmTokenService
) : FcmTokenRepository {
    override suspend fun save(token: String): Result<Unit, DataError> {
        return safeApiCall {
            fcmTokenService.sendToken(
                FcmTokenRequest(
                    token = token
                )
            )
        }
    }

    override suspend fun getFcmToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            null
        }
    }
}