package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result

interface FcmTokenRepository {
    suspend fun save(token: String): Result<Unit, DataError>
}