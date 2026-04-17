package com.avivba.simbanyu.ui.screens.billhistory

enum class BillingStatus(val status: String) {
    PAID("LUNAS"),
    UNPAID("BELUM DIBAYAR")
}

data class Bill(
    val id: String,
    val amount: String,
    val period: String,
    val status: BillingStatus,
)

data class BillHistoryUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val years: List<Int> = emptyList(),
    val selectedYear: Int = 0,
    val totalPaid : String = "",
    val totalUnpaid : String = "",
    val bills: List<Bill> = emptyList()
)

sealed interface BillHistoryAction {
    data object Load : BillHistoryAction
    data object Refresh : BillHistoryAction
    data class Filter(val year: Int) : BillHistoryAction
    data class ClickBill(val bill: Bill) : BillHistoryAction
}

sealed interface BillHistoryEvent {
    data class ShowBill(val bill: Bill) : BillHistoryEvent
}