package com.avivba.simbanyu.data.remote.model.auth

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.user.UserDto

@Keep
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)
