package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.subscriber.SubscriberDto
import retrofit2.http.GET
import retrofit2.http.Path

@Keep
interface SubscriberService {
    @GET("subscribers")
    suspend fun getAll(): Envelope<List<SubscriberDto>>

    @GET("subscribers/me")
    suspend fun getMe(): Envelope<SubscriberDto>

    @GET("subscribers/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Envelope<SubscriberDto>

}