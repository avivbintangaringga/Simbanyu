package com.avivba.simbanyu.data.local.tokenmanager

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking


val Context.tokenStore by dataStore("token", TokenDataSerializer)

class LocalTokenManagerImpl(
    context: Context,
) : LocalTokenManager {
    val store = context.tokenStore

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