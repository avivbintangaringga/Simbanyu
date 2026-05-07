package com.avivba.simbanyu.data.local.tokenmanager

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val accessToken: String? = null,
    val refreshToken: String? = null
)

