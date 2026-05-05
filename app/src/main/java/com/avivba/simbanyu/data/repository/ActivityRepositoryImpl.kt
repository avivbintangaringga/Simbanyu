package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.ActivityService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Activity
import com.avivba.simbanyu.domain.repository.ActivityRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

class ActivityRepositoryImpl(
    private val activityService: ActivityService
) : ActivityRepository {
    override suspend fun getAll(): Result<List<Activity>, DataError> {
        return Result.Error(DataError.AuthError)
    }
}