package com.avivba.simbanyu.data.remote.model.auth

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
