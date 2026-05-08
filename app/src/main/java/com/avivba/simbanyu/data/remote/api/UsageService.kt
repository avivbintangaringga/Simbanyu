package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.usage.UsageDto
import com.avivba.simbanyu.domain.model.UsageSubmission
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UsageService {
    @GET("usages")
    suspend fun getBySubscriber(
        @Query("subscriberId") subscriberId: String,
        @Query("limit") limit: Int
    ): Envelope<List<UsageDto>>

    @Multipart
    @POST("usages")
    suspend fun recordUsage(
        @Part("data") data: RequestBody,
        @Part("image") image: MultipartBody.Part
    ): Envelope<UsageDto>

    @Multipart
    @PUT("usages/{id}")
    suspend fun updateUsage(
        @Path("id") id: String,
        @Part("data") data: RequestBody,
        @Part("image") image: MultipartBody.Part?
    ): Envelope<UsageDto>
}