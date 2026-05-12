package com.avivba.simbanyu.ui.screens.billhistory

import com.avivba.simbanyu.ui.shared.model.UiBill

data class BillHistoryUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isMultipleSelectMode: Boolean = false,
    val years: List<Int> = emptyList(),
    val selectedYear: Int = 0,
    val totalSelectedBills: Double = 0.0,
    val bills: List<UiBill> = emptyList(),
)

sealed interface BillHistoryAction {
    data object Load : BillHistoryAction
    data object Refresh : BillHistoryAction
    data object ActivateMultipleSelectMode : BillHistoryAction
    data object PayMultipleBills : BillHistoryAction
    data class Filter(val year: Int) : BillHistoryAction
    data class ClickBill(val bill: UiBill) : BillHistoryAction
    data class LongClickBill(val bill: UiBill) : BillHistoryAction
}

sealed interface BillHistoryEvent {
    data class ShowBill(val bill: UiBill) : BillHistoryEvent
    data class PayBill(val billIds: List<String>) : BillHistoryEvent
}

val fakeBills = listOf<UiBill>()
