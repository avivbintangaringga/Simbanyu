package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.notification.NotificationDto
import retrofit2.http.GET

interface NotificationService {
    @GET("notifications")
    suspend fun getAll(): Envelope<List<NotificationDto>>
}