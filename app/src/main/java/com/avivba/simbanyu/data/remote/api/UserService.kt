package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.user.UserDto
import retrofit2.http.GET

interface UserService {
    @GET("users/me")
    suspend fun getMe(): Envelope<UserDto>
}