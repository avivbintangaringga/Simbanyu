package com.avivba.simbanyu.data.local.tokenmanager

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TokenData(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

