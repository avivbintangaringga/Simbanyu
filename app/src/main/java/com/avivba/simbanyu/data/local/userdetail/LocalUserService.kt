package com.avivba.simbanyu.data.local.userdetail

import androidx.annotation.Keep

@Keep
interface LocalUserService {
    fun saveUser(user: LocalUser)
    fun getUser(): LocalUser?
    fun clearUser()
}