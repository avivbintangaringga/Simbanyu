package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Announcement
import kotlinx.coroutines.launch

class AnnouncementViewModel :
    BaseViewModel<AnnouncementUiState, AnnouncementAction, AnnouncementEvent>(
        AnnouncementUiState()
    ) {

    init {
        load()
    }

    override fun onAction(action: AnnouncementAction) {
        viewModelScope.launch {
            when (action) {
                AnnouncementAction.Load -> load()
                AnnouncementAction.Refresh -> refresh()
                AnnouncementAction.CreateAnnouncement -> createAnnouncement()
                is AnnouncementAction.DeleteAnnouncement -> deleteAnnouncement(action.announcement)
                AnnouncementAction.NavigateBack -> sendEvent(AnnouncementEvent.NavigateBack)
                AnnouncementAction.ShowCreateDialog -> {
                    updateState {
                        it.copy(
                            isShowCreateDialog = true
                        )
                    }
                }
                AnnouncementAction.HideCreateDialog -> {
                    updateState {
                        it.copy(
                            isShowCreateDialog = false
                        )
                    }
                }
            }
        }
    }

    private fun load() {
        updateState {
            fakeAnnouncementUiState
        }
    }

    private fun refresh() {

    }

    private fun createAnnouncement() {

    }

    private fun deleteAnnouncement(announcement: Announcement) {

    }
}