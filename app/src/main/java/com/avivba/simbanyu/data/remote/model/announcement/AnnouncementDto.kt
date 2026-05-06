package com.avivba.simbanyu.data.remote.model.announcement

import com.avivba.simbanyu.domain.model.Announcement
import java.time.Instant

data class AnnouncementDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain(): Announcement = Announcement(
        id = id,
        title = title,
        body = body,
        date = Instant.parse(createdAt)
    )
}