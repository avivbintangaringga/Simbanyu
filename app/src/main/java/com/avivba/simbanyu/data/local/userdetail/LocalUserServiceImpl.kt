package com.avivba.simbanyu.data.local.userdetail

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class LocalUserServiceImpl(
    private val store: DataStore<LocalUser>
) : LocalUserService {
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