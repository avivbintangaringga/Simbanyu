package com.avivba.simbanyu.ui.screens.billhistory

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BillHistoryViewModel : BaseViewModel<BillHistoryUiState, BillHistoryAction, BillHistoryEvent>(
    BillHistoryUiState()
) {
    init {
        load()
    }

    override fun onAction(action: BillHistoryAction) {
        when (action) {
            BillHistoryAction.Load -> load()
            BillHistoryAction.Refresh -> refresh()
            is BillHistoryAction.Filter -> filter(action.year)
            is BillHistoryAction.ClickBill -> sendEvent(BillHistoryEvent.ShowBill(action.bill))
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isRefreshing = true
                )
            }
            delay(3000)
            updateState {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    private fun filter(year: Int) {
        updateState {
            it.copy(
                selectedYear = year
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true
                )
            }

            delay(1000)

            updateState {
                it.copy(
                    years = listOf(
                        2023,
                        2024,
                        2025,
                        2026
                    ),
                    selectedYear = 2026
                )
            }

            updateState {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}