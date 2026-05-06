package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.UserService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {
    override suspend fun getProfile(): Result<User, DataError> {
        return safeApiCall {
            userService.getMe().data?.toDomain() ?: throw Exception("Invalid response")
        }
    }
}