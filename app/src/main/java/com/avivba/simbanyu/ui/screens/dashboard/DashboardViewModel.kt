package com.avivba.simbanyu.ui.screens.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardViewModel : BaseViewModel<DashboardUiState, DashboardAction, DashboardEvent>(
    DashboardUiState()
) {
    override fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.Load -> load()
            DashboardAction.PayBill -> {}
            DashboardAction.ViewAllAnnouncements -> sendEvent(DashboardEvent.ViewAllAnnouncements)
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
                    waterUsageMaximum = 22.0,
                    waterUsageMaximumMonth = "FEB",
                    announcements = fakeAnnouncements,
                    isLoading = false
                )
            }
        }
    }
}