package com.avivba.simbanyu.ui.screens.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
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

            delay(3000)

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

            delay(1000)

            updateState {
                it.copy(
                    bill = Bill(
                        amount = "125.000",
                        period = "Januari 2023"
                    ),
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