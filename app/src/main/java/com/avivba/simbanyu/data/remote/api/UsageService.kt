package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.usage.UsageDto
import com.avivba.simbanyu.domain.model.UsageSubmission
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsageService {
    @GET("usages")
    suspend fun getAll(): Envelope<List<UsageDto>>

    @GET("usages?subscriberId={subscriberId}")
    suspend fun getForSubscriber(
        @Path("subscriberId") subscriberId: String
    ): Envelope<List<UsageDto>>

    @POST("usages")
    suspend fun recordUsage(
        @Body usage: UsageSubmission
    ): Envelope<UsageDto>

    @PUT("usages/{id}")
    suspend fun updateUsage(
        @Path("id") id: String,
        @Body usage: UsageSubmission
    ): Envelope<UsageDto>
}