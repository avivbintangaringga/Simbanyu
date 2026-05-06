package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.FcmTokenService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.FcmTokenRepository

class FcmTokenRepositoryImpl(
    private val fcmTokenService: FcmTokenService
): FcmTokenRepository {
    override suspend fun save(token: String): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}