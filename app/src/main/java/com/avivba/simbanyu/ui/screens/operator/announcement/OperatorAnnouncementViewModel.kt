package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Announcement
import kotlinx.coroutines.launch

class OperatorAnnouncementViewModel :
    BaseViewModel<OperatorAnnouncementUiState, OperatorAnnouncementAction, OperatorAnnouncementEvent>(
        OperatorAnnouncementUiState()
    ) {

    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: OperatorAnnouncementAction) {
        viewModelScope.launch {
            when (action) {
                OperatorAnnouncementAction.Load -> load()
                OperatorAnnouncementAction.Refresh -> refresh()
                OperatorAnnouncementAction.CreateAnnouncement -> createAnnouncement()
                is OperatorAnnouncementAction.DeleteAnnouncement -> deleteAnnouncement(action.announcement)
                OperatorAnnouncementAction.NavigateBack -> sendEvent(OperatorAnnouncementEvent.NavigateBack)
                OperatorAnnouncementAction.ShowCreateDialog -> {
                    updateState {
                        it.copy(
                            isShowCreateDialog = true
                        )
                    }
                }
                OperatorAnnouncementAction.HideCreateDialog -> {
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
            fakeOperatorAnnouncementUiState
        }
    }

    private fun refresh() {

    }

    private fun createAnnouncement() {

    }

    private fun deleteAnnouncement(announcement: Announcement) {

    }
}