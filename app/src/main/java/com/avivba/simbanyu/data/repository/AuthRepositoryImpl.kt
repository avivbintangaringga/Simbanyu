package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import com.avivba.simbanyu.data.local.userdetail.LocalUser
import com.avivba.simbanyu.data.local.userdetail.LocalUserService
import com.avivba.simbanyu.data.remote.api.AuthService
import com.avivba.simbanyu.data.remote.api.SubscriberService
import com.avivba.simbanyu.data.remote.model.auth.LoginRequest
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val subscriberService: SubscriberService,
    private val localUserService: LocalUserService,
    private val localTokenManager: LocalTokenManager
) : AuthRepository {
    override suspend fun isLoggedIn(): Boolean {
        return !localTokenManager.getAccessToken().isNullOrEmpty()
    }

    override suspend fun login(username: String, password: String): Result<User, DataError> {
        val loginData =
            safeApiCall {
                authService.login(
                    LoginRequest(
                        username = username,
                        password = password
                    )
                ).data ?: throw Exception("Invalid response")
            }


        when (loginData) {
            is Result.Success -> {
                val accessToken = loginData.data.accessToken
                val refreshToken = loginData.data.refreshToken
                val user = loginData.data.user.toDomain()

                localTokenManager.saveAccessToken(accessToken)
                localTokenManager.saveRefreshToken(refreshToken)

                var localUser = LocalUser(
                    id = user.id,
                    name = user.name,
                    username = user.username,
                    phoneNumber = user.phoneNumber,
                    role = user.name,
                    photoImageUrl = user.photoImageUrl,
                )

                if (user.role == User.UserRole.SUBSCRIBER) {
                    val subscriberResponse = safeApiCall {
                        subscriberService.getMe()
                            .data ?: throw Exception("Invalid response")
                    }

                    if (subscriberResponse is Result.Success) {
                        val subsData = subscriberResponse.data
                        localUser = localUser.copy(
                            subscriberId = subsData.id,
                            meterId = subsData.meterId,
                            plan = subsData.plan.name
                        )
                    } else {
                        localUserService.clearUser()
                        localTokenManager.clearToken()
                        return Result.Error(DataError.NetworkError.UNKNOWN)
                    }
                }

                localUserService.saveUser(localUser)
                return Result.Success(user)
            }

            is Result.Error -> {
                return Result.Error(loginData.error)
            }
        }
    }

    override suspend fun logout(username: String, password: String): Result<Unit, DataError> {
        localTokenManager.clearToken()
        localUserService.clearUser()

        return Result.Success(Unit)
    }
}