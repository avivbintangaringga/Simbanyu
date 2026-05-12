package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.ActivityRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OperatorDashboardViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<OperatorDashboardUiState, OperatorDashboardAction, OperatorDashboardEvent>(
    OperatorDashboardUiState()
) {
    init {
        viewModelScope.launch {
            refreshEventBus.refreshEvent
                .onStart { emit(Unit) }
                .collect {
                    load()
                }
        }

        viewModelScope.launch {
            userRepository.getLocalProfileFlow().collect { profile ->
                if (profile != null) {
                    updateState {
                        it.copy(
                            name = profile.name
                        )
                    }
                }
            }
        }
    }

    override fun onAction(action: OperatorDashboardAction) {
        viewModelScope.launch {
            when (action) {
                OperatorDashboardAction.Load -> load()
                OperatorDashboardAction.Refresh -> refresh()
                OperatorDashboardAction.ViewAllAnnouncements -> {
                    sendEvent(OperatorDashboardEvent.GoToAnnouncementsPage)
                }
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
        listOf(
            viewModelScope.async {
                when (val activities = activityRepository.getAll(limit = 3)) {
                    is Result.Success -> {
                        updateState {
                            it.copy(
                                activities = activities.data
                                    .sortedByDescending { it.date }
                                    .map {
                                        UiActivity.fromDomain(it)
                                    }
                            )
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            }
        ).awaitAll()
    }
}
