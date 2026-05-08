package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.user.UserDto
import retrofit2.http.GET

@Keep
interface UserService {
    @GET("users/me")
    suspend fun getMe(): Envelope<UserDto>
}