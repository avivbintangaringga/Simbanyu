package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.activity.ActivityDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ActivityService {
    @GET("activities")
    suspend fun getAll(): Envelope<List<ActivityDto>>
}