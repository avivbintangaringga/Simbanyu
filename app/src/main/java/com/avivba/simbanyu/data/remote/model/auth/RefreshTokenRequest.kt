package com.avivba.simbanyu.data.remote.model.auth

import androidx.annotation.Keep

@Keep
data class RefreshTokenRequest(
    val token: String
)
