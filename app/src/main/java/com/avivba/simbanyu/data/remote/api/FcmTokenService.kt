package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.fcmtoken.FcmTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

@Keep
interface FcmTokenService {
    @POST("fcmtoken")
    suspend fun sendToken(
        @Body token: FcmTokenRequest
    )
}