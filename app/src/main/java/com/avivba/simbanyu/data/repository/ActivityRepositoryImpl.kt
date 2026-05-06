package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.ActivityService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Activity
import com.avivba.simbanyu.domain.repository.ActivityRepository
import java.time.Instant

class ActivityRepositoryImpl(
    private val activityService: ActivityService
) : ActivityRepository {
    override suspend fun getAll(): Result<List<Activity>, DataError> {
        return safeApiCall {
            activityService.getAll().data?.map {
                Activity(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    date = Instant.now()
                )
            } ?: emptyList()
        }
    }
}