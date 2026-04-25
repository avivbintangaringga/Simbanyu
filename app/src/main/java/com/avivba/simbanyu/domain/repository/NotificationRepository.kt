package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Notification

interface NotificationRepository {
    suspend fun getAll(): Result<List<Notification>, DataError>
    suspend fun markAsRead(notification: Notification): Result<Unit, DataError>
}