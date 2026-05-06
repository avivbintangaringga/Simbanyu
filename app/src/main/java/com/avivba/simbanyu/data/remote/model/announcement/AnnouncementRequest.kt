package com.avivba.simbanyu.data.remote.model.announcement

import com.avivba.simbanyu.domain.model.Announcement

data class AnnouncementRequest(
    val title: String,
    val body: String,
) {
    companion object {
        fun fromDomain(data: Announcement) =
            AnnouncementRequest(
                title = data.title,
                body = data.body
            )
    }
}
