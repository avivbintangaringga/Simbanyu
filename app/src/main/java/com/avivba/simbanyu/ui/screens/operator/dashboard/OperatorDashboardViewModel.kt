package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.ActivityRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Activity
import kotlinx.coroutines.launch

class OperatorDashboardViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel<OperatorDashboardUiState, OperatorDashboardAction, OperatorDashboardEvent>(
    OperatorDashboardUiState()
) {
    init {
        viewModelScope.launch {
            load()
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

    private suspend fun fetchData() {
        when (val profile = userRepository.getLocalProfile()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        name = profile.data.name
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }

        when (val activities = activityRepository.getAll(limit = 3)) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        activities = activities.data
                            .sortedByDescending { it.date }
                            .map {
                                Activity.fromDomain(it)
                            }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}