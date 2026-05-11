package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.local.notification.LocalNotificationService
import com.avivba.simbanyu.data.remote.api.NotificationService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Notification
import com.avivba.simbanyu.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val notificationService: NotificationService,
    private val localNotificationService: LocalNotificationService
) : NotificationRepository {
    override suspend fun getAll(): Result<List<Notification>, DataError> {
        val readIds = localNotificationService.getReadIds()
        return safeApiCall {
            notificationService.getAll().data?.map {
                it.toDomain().copy(isRead = it.id in readIds)
            } ?: emptyList()
        }
    }

    override suspend fun markAsRead(id: String): Result<Unit, DataError> {
        localNotificationService.markAsRead(id)
        return Result.Success(Unit)
    }

    override suspend fun markAllAsRead(ids: List<String>): Result<Unit, DataError> {
        localNotificationService.markAllAsRead(ids)
        return Result.Success(Unit)
    }
}