package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.AuthService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(username: String, password: String): Result<User, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(username: String, password: String): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}