package com.avivba.simbanyu.data.remote.model.auth

import androidx.annotation.Keep

@Keep
data class LoginRequest(
    val username: String,
    val password: String
)
