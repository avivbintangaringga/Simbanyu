package com.avivba.simbanyu.data.local.tokenmanager

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.firstOrNull


val Context.tokenStore by dataStore("token", TokenDataSerializer)

class LocalTokenManagerImpl(
    context: Context,
) : LocalTokenManager {
    private val store = context.tokenStore

    override suspend fun getAccessToken(): String? {
        return store.data.firstOrNull()?.accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return store.data.firstOrNull()?.refreshToken
    }

    override suspend fun saveAccessToken(token: String) {
        store.updateData { it.copy(accessToken = token) }
    }

    override suspend fun saveRefreshToken(token: String) {
        store.updateData { it.copy(refreshToken = token) }
    }

    override suspend fun clearToken() {
        store.updateData { TokenData() }
    }
}
