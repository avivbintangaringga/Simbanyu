package com.avivba.simbanyu.data.local.notification

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.notificationStore by dataStore("notification", NotificationLocalSerializer)

class LocalNotificationServiceImpl(
    context: Context
) : LocalNotificationService {
    private val store = context.notificationStore

    override fun getReadIdsFlow(): Flow<Set<String>> {
        return store.data.map { it.readIds }
    }

    override suspend fun getReadIds(): Set<String> {
        return store.data.map { it.readIds }.first()
    }

    override suspend fun markAsRead(id: String) {
        store.updateData {
            it.copy(readIds = it.readIds + id)
        }
    }

    override suspend fun markAllAsRead(ids: List<String>) {
        store.updateData {
            it.copy(readIds = it.readIds + ids)
        }
    }
}
