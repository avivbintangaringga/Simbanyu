package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.NotificationService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Notification
import com.avivba.simbanyu.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val notificationService: NotificationService
) : NotificationRepository {
    override suspend fun getAll(): Result<List<Notification>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun markAsRead(notification: Notification): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}