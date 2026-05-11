package com.avivba.simbanyu.data.local.notification

import kotlinx.coroutines.flow.Flow

interface LocalNotificationService {
    fun getReadIdsFlow(): Flow<Set<String>>
    suspend fun getReadIds(): Set<String>
    suspend fun markAsRead(id: String)
    suspend fun markAllAsRead(ids: List<String>)
}
