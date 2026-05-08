package com.avivba.simbanyu.data.remote.authenticator

import androidx.annotation.Keep
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import com.avivba.simbanyu.data.local.userdetail.LocalUserService
import com.avivba.simbanyu.data.remote.api.AuthService
import com.avivba.simbanyu.data.remote.model.auth.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Keep
class TokenAuthenticator(
    private val localTokenManager: LocalTokenManager,
    private val localUserService: LocalUserService,
    private val authServiceProvider: () -> AuthService
) : Authenticator {
    private val authService by lazy { authServiceProvider() }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.countPriorResponses() >= 2) {
            handleSessionExpired()
            return null
        }

        synchronized(this) {
            val currentToken = runBlocking { localTokenManager.getAccessToken() }
            val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")

            if (requestToken != currentToken && currentToken != null) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            val refreshToken = runBlocking { localTokenManager.getRefreshToken() }
            if (refreshToken.isNullOrEmpty()) {
                handleSessionExpired()
                return null
            }

            return try {
                runBlocking {
                    val refreshResponse = authService.refresh(RefreshTokenRequest(refreshToken))
                    val newData = refreshResponse.data

                    if (newData != null) {
                        localTokenManager.saveAccessToken(newData.token)

                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${newData.token}")
                            .build()
                    } else {
                        handleSessionExpired()
                        null
                    }
                }
            } catch (e: Exception) {
                handleSessionExpired()
                null
            }
        }
    }

    private fun handleSessionExpired() {
        runBlocking {
            localTokenManager.clearToken()
            localUserService.clearUser()
        }
    }

    private fun Response.countPriorResponses(): Int {
        var result = 0
        var prior = priorResponse
        while (prior != null) {
            result++
            prior = prior.priorResponse
        }
        return result
    }
}
