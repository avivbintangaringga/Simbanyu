package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.notification.NotificationDto
import retrofit2.http.GET

@Keep
interface NotificationService {
    @GET("notifications")
    suspend fun getAll(): Envelope<List<NotificationDto>>
}