package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.announcement.AnnouncementDto
import com.avivba.simbanyu.domain.model.AnnouncementSubmission
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnnouncementService {
    @GET("announcements")
    suspend fun getAll(): Envelope<List<AnnouncementDto>>

    @GET("announcements/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Envelope<List<AnnouncementDto>>

    @POST("announcements")
    suspend fun add(
        @Body announcement: AnnouncementSubmission
    ): Envelope<AnnouncementDto>

    @PUT("announcements/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body announcement: AnnouncementSubmission
    ): Envelope<AnnouncementDto>

    @DELETE("announcements/{id}")
    suspend fun delete(
        @Path("id") id: String
    )
}