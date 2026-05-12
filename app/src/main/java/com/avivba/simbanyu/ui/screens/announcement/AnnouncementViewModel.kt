package com.avivba.simbanyu.ui.screens.announcement

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiAnnouncement
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val announcementRepository: AnnouncementRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<AnnouncementUiState, AnnouncementAction, AnnouncementEvent>(
    AnnouncementUiState()
) {

    init {
        viewModelScope.launch {
            refreshEventBus.refreshEvent
                .onStart { emit(Unit) }
                .collect {
                    load()
                }
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
        if (state.value.isLoading) return
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
                            .map { UiAnnouncement.fromDomain(it) }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}
