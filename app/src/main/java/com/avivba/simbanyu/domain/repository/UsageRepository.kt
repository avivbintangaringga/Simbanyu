package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.domain.model.UsageSubmission
import com.avivba.simbanyu.domain.model.UsageUpdateSubmission

interface UsageRepository {
    suspend fun getAll(
        subscriberId: String?,
        limit: Int? = null,
        includeInitial: Boolean = false
    ): Result<List<Usage>, DataError>

    suspend fun getById(id: String): Result<Usage, DataError>
    suspend fun getLastUsage(subscriberId: String, includeInitial: Boolean = false): Result<Usage, DataError>
    suspend fun recordUsage(usage: UsageSubmission): Result<Usage, DataError>
    suspend fun updateUsage(id: String, usage: UsageUpdateSubmission): Result<Usage, DataError>
}