package com.avivba.simbanyu.ui.screens.operator.announcement

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.AnnouncementSubmission
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiAnnouncement
import kotlinx.coroutines.launch

class OperatorAnnouncementViewModel(
    private val announcementRepository: AnnouncementRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<OperatorAnnouncementUiState, OperatorAnnouncementAction, OperatorAnnouncementEvent>(
    OperatorAnnouncementUiState()
) {

    init {
        viewModelScope.launch {
            load()
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                load()
            }
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

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isLoading = true
            )
        }
    }

    private suspend fun refresh() {
        updateState {
            it.copy(
                isRefreshing = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isRefreshing = true
            )
        }
    }

    private suspend fun fetchData() {
        when (val announcements = announcementRepository.getAllAnnouncements()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        announcements = announcements.data
                            .sortedByDescending { it.date }
                            .map { item ->
                                UiAnnouncement.fromDomain(item)
                            }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }

    private suspend fun createAnnouncement() {
        if (!state.value.isCreatingAnnouncement) {
            updateState {
                it.copy(
                    errorFields = emptyList()
                )
            }

            if (!validateFields()) {
                return
            }

            updateState {
                it.copy(
                    isCreatingAnnouncement = true
                )
            }

            val submission = AnnouncementSubmission(
                title = state.value.titleState.text.toString(),
                body = state.value.descriptionState.text.toString()
            )

            when (announcementRepository.addAnnouncement(submission)) {
                is Result.Success -> {
                    sendEvent(OperatorAnnouncementEvent.AnnouncementCreated)
                    refreshEventBus.triggerRefresh()

                    updateState {
                        it.copy(
                            titleState = TextFieldState(),
                            descriptionState = TextFieldState(),
                            isShowCreateDialog = false
                        )
                    }
                }

                is Result.Error -> {
                    // TODO: Handle error
                }
            }

            updateState {
                it.copy(
                    isCreatingAnnouncement = false
                )
            }
        }
    }

    private suspend fun deleteAnnouncement(announcement: UiAnnouncement) {
        when (announcementRepository.deleteAnnouncement(announcement.id)) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        announcements = it.announcements.filter { item ->
                            item.id != announcement.id
                        }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
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