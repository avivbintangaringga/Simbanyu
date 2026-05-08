package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.report.ReportDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

@Keep
interface ReportService {
    @GET("reports")
    suspend fun getAll(): Envelope<List<ReportDto>>

    @Multipart
    @POST("reports")
    suspend fun add(
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part
    ): Envelope<ReportDto>

    @Multipart
    @PUT("reports/{id}")
    suspend fun handleReport(
        @Path("id") id: String,
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part
    ): Envelope<ReportDto>
}