package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.SubscriberService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Subscriber
import com.avivba.simbanyu.domain.repository.SubscriberRepository

class SubscriberRepositoryImpl(
    private val subscriberService: SubscriberService
) : SubscriberRepository {
    override suspend fun getAll(): Result<List<Subscriber>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Result<Subscriber, DataError> {
        TODO("Not yet implemented")
    }
}