package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.usage.UsageDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

@Keep
interface UsageService {
    @GET("usages")
    suspend fun getAll(
        @Query("subscriberId") subscriberId: String?,
        @Query("limit") limit: Int? = null,
        @Query("includeInitial") includeInitial: Boolean? = false
    ): Envelope<List<UsageDto>>

    @GET("usages/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Envelope<UsageDto>

    @Multipart
    @POST("usages")
    suspend fun recordUsage(
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part
    ): Envelope<UsageDto>

    @Multipart
    @PUT("usages/{id}")
    suspend fun updateUsage(
        @Path("id") id: String,
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part?
    ): Envelope<UsageDto>
}