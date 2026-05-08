package com.avivba.simbanyu.data.local.tokenmanager

import androidx.annotation.Keep

import kotlinx.coroutines.flow.Flow

@Keep
interface LocalTokenManager {
    fun getAccessTokenFlow(): Flow<String?>
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearToken()
}