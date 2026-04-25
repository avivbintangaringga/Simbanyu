package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User, DataError>
    suspend fun logout(username: String, password: String): Result<Unit, DataError>
}