package com.avivba.simbanyu.data.remote.model.auth

import com.avivba.simbanyu.data.remote.model.user.UserDto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)
