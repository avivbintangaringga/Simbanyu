package com.avivba.simbanyu.ui.screens.operator.announcement

import com.avivba.simbanyu.ui.shared.model.Announcement

data class AnnouncementUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val announcements: List<Announcement> = emptyList(),
    val isShowCreateDialog: Boolean = false
)

sealed interface AnnouncementAction {
    data object Load : AnnouncementAction
    data object Refresh : AnnouncementAction
    data object ShowCreateDialog : AnnouncementAction
    data object HideCreateDialog : AnnouncementAction
    data object CreateAnnouncement : AnnouncementAction
    data class DeleteAnnouncement(val announcement: Announcement) : AnnouncementAction
    data object NavigateBack : AnnouncementAction
}

sealed interface AnnouncementEvent {
    data object NavigateBack : AnnouncementEvent
}

val fakeAnnouncements = listOf(
    Announcement(
        id = "1",
        title = "Pipa Bocor",
        content = "Terjadi kebocoran pipa di area pusat kota.",
        time = "10:00 AM"
    ),
    Announcement(
        id = "2",
        title = "Pemeliharaan Rutin",
        content = "Pemeliharaan berkala untuk wilayah utara.",
        time = "09:00 AM"
    ),
    Announcement(
        id = "3",
        title = "Himbauan Hemat Air",
        content = "Gunakan air secukupnya selama musim kemarau.",
        time = "Yesterday"
    )
)

val fakeAnnouncementUiState = AnnouncementUiState(
    isLoading = false,
    isRefreshing = false,
    announcements = fakeAnnouncements
)
