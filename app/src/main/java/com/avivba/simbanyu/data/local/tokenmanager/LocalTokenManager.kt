package com.avivba.simbanyu.data.local.tokenmanager

interface LocalTokenManager {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun clearToken()
}