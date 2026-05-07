package com.avivba.simbanyu.data.local.tokenmanager

interface LocalTokenManager {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearToken()
}