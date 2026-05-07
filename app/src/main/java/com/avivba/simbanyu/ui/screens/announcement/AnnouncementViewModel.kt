package com.avivba.simbanyu.ui.screens.announcement

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Announcement
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val announcementRepository: AnnouncementRepository
) : BaseViewModel<AnnouncementUiState, AnnouncementAction, AnnouncementEvent>(
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

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isLoading = false
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
                isRefreshing = false
            )
        }
    }

    private suspend fun fetchData() {
        when(val announcements = announcementRepository.getAllAnnouncements()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        announcements = announcements.data
                            .sortedByDescending { it.date }
                            .map { Announcement.fromDomain(it) }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}