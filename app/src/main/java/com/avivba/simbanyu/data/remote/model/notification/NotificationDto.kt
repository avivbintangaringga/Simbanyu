package com.avivba.simbanyu.data.remote.model.notification

import com.avivba.simbanyu.domain.model.Notification
import java.time.Instant

data class NotificationDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain() = Notification(
        id = id,
        title = title,
        body = body,
        date = Instant.parse(createdAt)
    )
}