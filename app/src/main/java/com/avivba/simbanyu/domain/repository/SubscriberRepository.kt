package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Subscriber

interface SubscriberRepository {
    suspend fun getAll(): Result<List<Subscriber>, DataError>
    suspend fun getById(id: String): Result<Subscriber, DataError>
}