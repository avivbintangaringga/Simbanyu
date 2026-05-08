package com.avivba.simbanyu.data.remote.model.notification

import androidx.annotation.Keep
import com.avivba.simbanyu.domain.model.Notification
import java.time.Instant

@Keep
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