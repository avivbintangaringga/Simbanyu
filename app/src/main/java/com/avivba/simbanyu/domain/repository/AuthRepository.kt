package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isLoggedInFlow(): Flow<Boolean>
    suspend fun isLoggedIn(): Boolean
    suspend fun login(username: String, password: String): Result<User, DataError>
    suspend fun logout(): Result<Unit, DataError>
}