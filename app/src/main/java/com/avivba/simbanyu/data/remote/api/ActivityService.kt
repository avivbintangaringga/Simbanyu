package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.activity.ActivityDto
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
interface ActivityService {
    @GET("activities")
    suspend fun getAll(
        @Query("limit") limit: Int? = null
    ): Envelope<List<ActivityDto>>
}