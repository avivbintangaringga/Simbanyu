package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.report.ReportDto
import com.avivba.simbanyu.data.remote.model.report.ReportSubmission
import com.avivba.simbanyu.data.remote.model.report.ReportUpdateSubmission
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ReportService {
    @GET("reports")
    suspend fun getAll(): Envelope<List<ReportDto>>

    @Multipart
    @POST("reports")
    suspend fun add(
        @Part("data") data: RequestBody,
        @Part("image") image: MultipartBody.Part
    ): Envelope<ReportDto>

    @Multipart
    @PUT("reports/{id}")
    suspend fun handleReport(
        @Path("id") id: String,
        @Part("data") data: RequestBody,
        @Part("image") image: MultipartBody.Part
    ): Envelope<ReportDto>
}