package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.domain.model.UsageSubmission

interface UsageRepository {
    suspend fun getAll(subscriberId: String, limitLast: Int = 0): Result<Unit, DataError>
    suspend fun getLastUsage(subscriberId: String): Result<Usage, DataError>
    suspend fun recordUsage(usage: UsageSubmission): Result<Usage, DataError>
    suspend fun updateUsage(usage: Usage): Result<Usage, DataError>
}