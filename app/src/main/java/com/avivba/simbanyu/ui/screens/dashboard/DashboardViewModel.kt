package com.avivba.simbanyu.ui.screens.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.core.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardViewModel : BaseViewModel<DashboardUiState, DashboardAction, DashboardEvent>(
    DashboardUiState()
) {
    init {
        onAction(DashboardAction.Load)
    }

    override fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.Load -> load()
            DashboardAction.PayBill -> {}
            DashboardAction.ViewAllAnnouncements -> sendEvent(DashboardEvent.ViewAllAnnouncements)
            DashboardAction.Refresh -> refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isRefreshing = true,
                    isLoading = true
                )
            }

            updateState {
                it.copy(
                    isRefreshing = false,
                    isLoading = false
                )
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    name = "Budiman Man"
                )
            }

            updateState {
                it.copy(
                    bill = "125.000",
                    waterUsage = fakeWaterUsage,
                    waterUsageAverage = 14.3,
                    waterUsageMaximum = 22,
                    waterUsageMaximumMonth = "FEB",
                    announcements = fakeAnnouncements,
                    isLoading = false
                )
            }

            delay(5000)
        }
    }
}