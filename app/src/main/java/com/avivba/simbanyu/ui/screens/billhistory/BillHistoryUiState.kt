package com.avivba.simbanyu.ui.screens.billhistory

import com.avivba.simbanyu.ui.shared.model.Bill
import com.avivba.simbanyu.ui.shared.model.BillingStatus

data class BillHistoryUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isMultipleSelectMode: Boolean = false,
    val years: List<Int> = emptyList(),
    val selectedYear: Int = 0,
    val totalSelectedBills : Double = 0.0,
    val bills: List<Bill> = emptyList(),
)

sealed interface BillHistoryAction {
    data object Load : BillHistoryAction
    data object Refresh : BillHistoryAction
    data object ActivateMultipleSelectMode : BillHistoryAction
    data object PayMultipleBills : BillHistoryAction
    data class Filter(val year: Int) : BillHistoryAction
    data class ClickBill(val bill: Bill) : BillHistoryAction
    data class LongClickBill(val bill: Bill) : BillHistoryAction
}

sealed interface BillHistoryEvent {
    data class ShowBill(val bill: Bill) : BillHistoryEvent
    data class PayBill(val billIds: List<String>) : BillHistoryEvent
}

val fakeBills = listOf(
    // 2024
    Bill("24-1", "150.000", "Januari", 2024, BillingStatus.PAID),
    Bill("24-2", "165.000", "Februari", 2024, BillingStatus.PAID),
    Bill("24-3", "140.000", "Maret", 2024, BillingStatus.PAID),
    Bill("24-4", "155.000", "April", 2024, BillingStatus.PENDING),
    Bill("24-5", "145.000", "Mei", 2024, BillingStatus.PENDING),
    Bill("24-6", "160.000", "Juni", 2024, BillingStatus.PENDING),
    Bill("24-7", "150.000", "Juli", 2024, BillingStatus.UNPAID),
    Bill("24-8", "155.000", "Agustus", 2024, BillingStatus.UNPAID),
    Bill("24-9", "140.000", "September", 2024, BillingStatus.UNPAID),
    Bill("24-10", "165.000", "Oktober", 2024, BillingStatus.UNPAID),
    Bill("24-11", "150.000", "November", 2024, BillingStatus.UNPAID),
    Bill("24-12", "170.000", "Desember", 2024, BillingStatus.UNPAID),
    // 2023
    Bill("23-1", "145.000", "Januari", 2023, BillingStatus.PAID),
    Bill("23-2", "150.000", "Februari", 2023, BillingStatus.PAID),
    Bill("23-3", "140.000", "Maret", 2023, BillingStatus.PAID),
    Bill("23-4", "155.000", "April", 2023, BillingStatus.PAID),
    Bill("23-5", "150.000", "Mei", 2023, BillingStatus.PAID),
    Bill("23-6", "145.000", "Juni", 2023, BillingStatus.PAID),
    Bill("23-7", "160.000", "Juli", 2023, BillingStatus.PAID),
    Bill("23-8", "155.000", "Agustus", 2023, BillingStatus.PAID),
    Bill("23-9", "140.000", "September", 2023, BillingStatus.PAID),
    Bill("23-10", "165.000", "Oktober", 2023, BillingStatus.PAID),
    Bill("23-11", "150.000", "November", 2023, BillingStatus.PAID),
    Bill("23-12", "170.000", "Desember", 2023, BillingStatus.PAID),
    // 2022
    Bill("22-1", "140.000", "Januari", 2022, BillingStatus.PAID),
    Bill("22-2", "145.000", "Februari", 2022, BillingStatus.PAID),
    Bill("22-3", "135.000", "Maret", 2022, BillingStatus.PAID),
    Bill("22-4", "150.000", "April", 2022, BillingStatus.PAID),
    Bill("22-5", "145.000", "Mei", 2022, BillingStatus.PAID),
    Bill("22-6", "140.000", "Juni", 2022, BillingStatus.PAID),
    Bill("22-7", "155.000", "Juli", 2022, BillingStatus.PAID),
    Bill("22-8", "150.000", "Agustus", 2022, BillingStatus.PAID),
    Bill("22-9", "135.000", "September", 2022, BillingStatus.PAID),
    Bill("22-10", "160.000", "Oktober", 2022, BillingStatus.PAID),
    Bill("22-11", "145.000", "November", 2022, BillingStatus.PAID),
    Bill("22-12", "165.000", "Desember", 2022, BillingStatus.PAID),
)
