package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.local.userdetail.LocalUserService
import com.avivba.simbanyu.data.remote.api.UserService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Profile
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userService: UserService,
    private val localUserService: LocalUserService
) : UserRepository {
    override suspend fun getCurrentUser(): Result<User, DataError> {
        val userData = safeApiCall {
            userService.getMe().data?.toDomain() ?: throw Exception("Invalid response")
        }

        return userData
    }

    override suspend fun getLocalProfile(): Result<Profile, DataError> {
        val localUser = localUserService.getUser() ?: return Result.Error(DataError.NotFound)
        if (localUser.id.isEmpty()) return Result.Error(DataError.NotFound)
        return Result.Success(localUser.toDomain())
    }

    override fun getLocalProfileFlow(): Flow<Profile?> {
        return localUserService.getUserFlow().map { it?.toDomain() }
    }
}