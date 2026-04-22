package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Announcement
import kotlinx.coroutines.delay
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
        if (!state.value.isCreatingAnnouncement) {
            updateState {
                it.copy(
                    errorFields = emptyList()
                )
            }

            if (!validateFields()) {
                return
            }

            viewModelScope.launch {
                updateState {
                    it.copy(
                        isCreatingAnnouncement = true
                    )
                }

                delay(2000)

                // TODO: Implement create announcement

                updateState {
                    it.copy(
                        titleState = TextFieldState(),
                        descriptionState = TextFieldState(),
                        isCreatingAnnouncement = false
                    )
                }
                sendEvent(OperatorAnnouncementEvent.AnnouncementCreated)
                load()
            }
        }
    }

    private fun deleteAnnouncement(announcement: Announcement) {

    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (state.value.titleState.text.isEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.TITLE
                )
            }

            isValid = false
        }

        if (state.value.descriptionState.text.isEmpty()) {
            updateState {
                it.copy(
                    errorFields = it.errorFields + Fields.DESCRIPTION
                )
            }

            isValid = false
        }

        return isValid
    }
}