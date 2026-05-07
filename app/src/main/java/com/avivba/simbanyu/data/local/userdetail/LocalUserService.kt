package com.avivba.simbanyu.data.local.userdetail

interface LocalUserService {
    fun saveUser(user: LocalUser)
    fun getUser(): LocalUser?
    fun clearUser()
}