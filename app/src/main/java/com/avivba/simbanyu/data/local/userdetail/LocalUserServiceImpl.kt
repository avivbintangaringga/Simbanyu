package com.avivba.simbanyu.data.local.userdetail

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userStore by dataStore("user", LocalUserSerializer)

class LocalUserServiceImpl(
    context: Context,
) : LocalUserService {
    private val store = context.userStore

    override suspend fun saveUser(user: LocalUser) {
        store.updateData { user }
    }

    override fun getUserFlow(): Flow<LocalUser?> {
        return store.data.map { if (it.id.isEmpty()) null else it }
    }

    override suspend fun clearUser() {
        store.updateData { LocalUser() }
    }
}