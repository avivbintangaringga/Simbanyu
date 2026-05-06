package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.report.ReportDto
import com.avivba.simbanyu.data.remote.model.report.ReportSubmission
import com.avivba.simbanyu.data.remote.model.report.ReportUpdateSubmission
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReportService {
    @GET("reports")
    suspend fun getAll(): Envelope<List<ReportDto>>

    @POST("reports")
    suspend fun add(
        @Body report: ReportSubmission
    ): Envelope<ReportDto>

    @PUT("reports/{id}")
    suspend fun handleReport(
        @Path("id") id: String,
        @Body report: ReportUpdateSubmission
    )
}