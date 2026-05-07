package com.avivba.simbanyu.data.local.userdetail

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

val Context.userStore by dataStore("user", LocalUserSerializer)

class LocalUserServiceImpl(
    context: Context,
) : LocalUserService {
    val store = context.userStore

    override fun saveUser(user: LocalUser) {
        runBlocking {
            store.updateData { user }
        }
    }

    override fun getUser(): LocalUser? {
        return runBlocking {
            store.data.firstOrNull()
        }
    }

    override fun clearUser() {
        runBlocking {
            store.updateData { LocalUser() }
        }
    }
}