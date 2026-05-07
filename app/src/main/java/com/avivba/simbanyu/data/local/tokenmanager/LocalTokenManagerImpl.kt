package com.avivba.simbanyu.data.local.tokenmanager

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking


class LocalTokenManagerImpl(
    private val store: DataStore<TokenData>
) : LocalTokenManager {
    override fun getAccessToken(): String? {
        return runBlocking { store.data.firstOrNull() }?.accessToken
    }

    override fun getRefreshToken(): String? {
        return runBlocking { store.data.firstOrNull() }?.refreshToken
    }

    override fun saveAccessToken(token: String) {
        runBlocking {
            store.updateData {
                it.copy(accessToken = token)
            }
        }
    }

    override fun saveRefreshToken(token: String) {
        runBlocking {
            store.updateData {
                it.copy(refreshToken = token)
            }
        }
    }

    override fun clearToken() {
        runBlocking {
            store.updateData {
                TokenData()
            }
        }
    }
}