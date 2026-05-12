package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.UiAnnouncement

enum class Fields {
    TITLE,
    DESCRIPTION
}

data class OperatorAnnouncementUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val announcements: List<UiAnnouncement> = emptyList(),
    val isShowCreateDialog: Boolean = false,
    val isCreatingAnnouncement: Boolean = false,
    val titleState: TextFieldState = TextFieldState(),
    val descriptionState: TextFieldState = TextFieldState(),
    val errorFields: List<Fields> = emptyList()
)

sealed interface OperatorAnnouncementAction {
    data object Load : OperatorAnnouncementAction
    data object Refresh : OperatorAnnouncementAction
    data object ShowCreateDialog : OperatorAnnouncementAction
    data object HideCreateDialog : OperatorAnnouncementAction
    data object CreateAnnouncement : OperatorAnnouncementAction
    data class DeleteAnnouncement(val announcement: UiAnnouncement) : OperatorAnnouncementAction
    data object NavigateBack : OperatorAnnouncementAction
}

sealed interface OperatorAnnouncementEvent {
    data object AnnouncementCreated : OperatorAnnouncementEvent
    data object NavigateBack : OperatorAnnouncementEvent
}

val fakeAnnouncements = listOf(
    UiAnnouncement(
        id = "1",
        title = "Pipa Bocor",
        body = "Terjadi kebocoran pipa di area pusat kota.",
        date = "10:00 AM"
    ),
    UiAnnouncement(
        id = "2",
        title = "Pemeliharaan Rutin",
        body = "Pemeliharaan berkala untuk wilayah utara.",
        date = "09:00 AM"
    ),
    UiAnnouncement(
        id = "3",
        title = "Himbauan Hemat Air",
        body = "Gunakan air secukupnya selama musim kemarau.",
        date = "Yesterday"
    )
)

val fakeOperatorAnnouncementUiState = OperatorAnnouncementUiState(
    isLoading = false,
    isRefreshing = false,
    announcements = fakeAnnouncements
)
