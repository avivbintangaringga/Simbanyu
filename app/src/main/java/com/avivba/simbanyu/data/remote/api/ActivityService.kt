package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.ApiResponse
import com.avivba.simbanyu.data.remote.model.activity.ActivityDto
import retrofit2.http.GET

interface ActivityService {
    @GET("activities")
    suspend fun getAll(): ApiResponse<List<ActivityDto>>
}