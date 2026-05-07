package com.avivba.simbanyu.ui.screens.announcement

import com.avivba.simbanyu.ui.shared.model.Announcement

data class AnnouncementUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val announcements: List<Announcement> = emptyList(),
)

sealed interface AnnouncementAction {
    data object Load : AnnouncementAction
    data object Refresh : AnnouncementAction
    data object NavigateBack : AnnouncementAction
}

sealed interface AnnouncementEvent {
    data object NavigateBack : AnnouncementEvent
}

val fakeAnnouncements = listOf(
    Announcement(
        id = "1",
        title = "Pipa Bocor",
        body = "Terjadi kebocoran pipa di area pusat kota.",
        date = "10:00 AM"
    ),
    Announcement(
        id = "2",
        title = "Pemeliharaan Rutin",
        body = "Pemeliharaan berkala untuk wilayah utara.",
        date = "09:00 AM"
    ),
    Announcement(
        id = "3",
        title = "Himbauan Hemat Air",
        body = "Gunakan air secukupnya selama musim kemarau.",
        date = "Yesterday"
    )
)

val fakeOperatorAnnouncementUiState = AnnouncementUiState(
    isLoading = false,
    isRefreshing = false,
    announcements = fakeAnnouncements
)
