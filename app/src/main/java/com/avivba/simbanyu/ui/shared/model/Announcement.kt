package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toRelativeTime

data class Announcement(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
) {
    companion object {
        fun fromDomain(announcement: com.avivba.simbanyu.domain.model.Announcement): Announcement {
            return Announcement(
                id = announcement.id,
                title = announcement.title,
                body = announcement.body,
                date = announcement.date.toRelativeTime(),
            )
        }
    }
}

