package com.avivba.simbanyu.ui.screens.operator.announcement

import com.avivba.simbanyu.ui.shared.model.Announcement

data class OperatorAnnouncementUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val announcements: List<Announcement> = emptyList(),
    val isShowCreateDialog: Boolean = false
)

sealed interface OperatorAnnouncementAction {
    data object Load : OperatorAnnouncementAction
    data object Refresh : OperatorAnnouncementAction
    data object ShowCreateDialog : OperatorAnnouncementAction
    data object HideCreateDialog : OperatorAnnouncementAction
    data object CreateAnnouncement : OperatorAnnouncementAction
    data class DeleteAnnouncement(val announcement: Announcement) : OperatorAnnouncementAction
    data object NavigateBack : OperatorAnnouncementAction
}

sealed interface OperatorAnnouncementEvent {
    data object NavigateBack : OperatorAnnouncementEvent
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

val fakeOperatorAnnouncementUiState = OperatorAnnouncementUiState(
    isLoading = false,
    isRefreshing = false,
    announcements = fakeAnnouncements
)
