package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.auth.LoginRequest
import com.avivba.simbanyu.data.remote.model.auth.LoginResponseDto
import com.avivba.simbanyu.data.remote.model.auth.RefreshTokenRequest
import com.avivba.simbanyu.data.remote.model.auth.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

@Keep
interface AuthService {
    @retrofit2.http.Headers("No-Auth: true")
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Envelope<LoginResponseDto>

    @retrofit2.http.Headers("No-Auth: true")
    @POST("auth/refresh")
    suspend fun refresh(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Envelope<TokenResponseDto>
}