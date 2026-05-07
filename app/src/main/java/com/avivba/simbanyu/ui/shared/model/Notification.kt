package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toRelativeTime

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val isRead: Boolean
) {
    companion object {
        fun fromDomain(notification: com.avivba.simbanyu.domain.model.Notification): Notification {
            return Notification(
                id = notification.id,
                title = notification.title,
                body = notification.body,
                date = notification.date.toRelativeTime(),
                isRead = false // TODO: Check local storage
            )
        }
    }
}
