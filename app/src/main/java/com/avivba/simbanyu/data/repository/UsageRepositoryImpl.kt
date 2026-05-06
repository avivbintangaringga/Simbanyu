package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.UsageService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.domain.model.UsageSubmission
import com.avivba.simbanyu.domain.model.UsageUpdateSubmission
import com.avivba.simbanyu.domain.repository.UsageRepository

class UsageRepositoryImpl(
    private val usageService: UsageService
) : UsageRepository {
    override suspend fun getAll(subscriberId: String, limitLast: Int): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getLastUsage(subscriberId: String): Result<Usage, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun recordUsage(usage: UsageSubmission): Result<Usage, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUsage(
        id: String,
        usage: UsageUpdateSubmission
    ): Result<Usage, DataError> {
        TODO("Not yet implemented")
    }
}