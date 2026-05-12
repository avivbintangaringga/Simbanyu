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
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val subscriberService: SubscriberService,
    private val localUserService: LocalUserService,
    private val localTokenManager: LocalTokenManager
) : AuthRepository {
    override fun isLoggedInFlow(): Flow<Boolean> {
        return localTokenManager.getAccessTokenFlow().map { !it.isNullOrEmpty() }
    }

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
                    role = user.role.name,
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
                            plan = subsData.plan.name,
                            address = subsData.address,
                            latitude = subsData.latitude,
                            longitude = subsData.longitude
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

    override suspend fun logout(): Result<Unit, DataError> {
        return try {
            safeApiCall {
                authService.logout()
            }
            FirebaseMessaging.getInstance().deleteToken()
            localTokenManager.clearToken()
            localUserService.clearUser()
            Result.Success(Unit)
        } catch (_: Exception) {
            localTokenManager.clearToken()
            localUserService.clearUser()
            Result.Success(Unit)
        }
    }
}