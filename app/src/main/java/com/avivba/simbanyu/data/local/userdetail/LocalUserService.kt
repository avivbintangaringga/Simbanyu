package com.avivba.simbanyu.data.local.userdetail

import androidx.annotation.Keep
import kotlinx.coroutines.flow.Flow

@Keep
interface LocalUserService {
    suspend fun saveUser(user: LocalUser)
    fun getUserFlow(): Flow<LocalUser?>
    suspend fun clearUser()
}