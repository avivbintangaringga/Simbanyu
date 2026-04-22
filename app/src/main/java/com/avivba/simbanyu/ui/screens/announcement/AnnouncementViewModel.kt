package com.avivba.simbanyu.ui.screens.announcement

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class AnnouncementViewModel :
    BaseViewModel<AnnouncementUiState, AnnouncementAction, AnnouncementEvent>(
        AnnouncementUiState()
    ) {

    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: AnnouncementAction) {
        viewModelScope.launch {
            when (action) {
                AnnouncementAction.Load -> load()
                AnnouncementAction.Refresh -> refresh()
                AnnouncementAction.NavigateBack -> sendEvent(AnnouncementEvent.NavigateBack)
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
}