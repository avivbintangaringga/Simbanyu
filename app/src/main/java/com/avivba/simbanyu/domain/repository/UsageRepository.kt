package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.MeterRecord

interface UsageRepository {
    suspend fun getAll(subscriberId: String, limitLast: Int = 0): Result<Unit, DataError>
    suspend fun upsertMeter(subscriberId: String, record: MeterRecord): Result<Unit, DataError>
}