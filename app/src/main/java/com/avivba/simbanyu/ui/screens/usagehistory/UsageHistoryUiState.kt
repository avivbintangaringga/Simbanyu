package com.avivba.simbanyu.ui.screens.usagehistory

enum class BillingStatus(val status: String) {
    PAID("LUNAS"),
    UNPAID("BELUM DIBAYAR")
}

data class Usage(
    val id: String,
    val amount: String,
    val periodMonth: String,
    val periodYear: Int,
    val status: BillingStatus,
)

data class UsageHistoryUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val years: List<Int> = emptyList(),
    val selectedYear: Int = 0,
    val totalPaid : String = "",
    val totalUnpaid : String = "",
    val usages: List<Usage> = emptyList()
)

sealed interface UsageHistoryAction {
    data object Load : UsageHistoryAction
    data object Refresh : UsageHistoryAction
    data class Filter(val year: Int) : UsageHistoryAction
    data class ClickUsage(val usage: Usage) : UsageHistoryAction
}

sealed interface UsageHistoryEvent {
    data class ShowUsage(val usage: Usage) : UsageHistoryEvent
}

val fakeUsages = listOf(
    // 2024
    Usage("24-1", "150.000", "Januari", 2024, BillingStatus.PAID),
    Usage("24-2", "165.000", "Februari", 2024, BillingStatus.PAID),
    Usage("24-3", "140.000", "Maret", 2024, BillingStatus.PAID),
    Usage("24-4", "155.000", "April", 2024, BillingStatus.UNPAID),
    Usage("24-5", "145.000", "Mei", 2024, BillingStatus.UNPAID),
    Usage("24-6", "160.000", "Juni", 2024, BillingStatus.UNPAID),
    Usage("24-7", "150.000", "Juli", 2024, BillingStatus.UNPAID),
    Usage("24-8", "155.000", "Agustus", 2024, BillingStatus.UNPAID),
    Usage("24-9", "140.000", "September", 2024, BillingStatus.UNPAID),
    Usage("24-10", "165.000", "Oktober", 2024, BillingStatus.UNPAID),
    Usage("24-11", "150.000", "November", 2024, BillingStatus.UNPAID),
    Usage("24-12", "170.000", "Desember", 2024, BillingStatus.UNPAID),
    // 2023
    Usage("23-1", "145.000", "Januari", 2023, BillingStatus.PAID),
    Usage("23-2", "150.000", "Februari", 2023, BillingStatus.PAID),
    Usage("23-3", "140.000", "Maret", 2023, BillingStatus.PAID),
    Usage("23-4", "155.000", "April", 2023, BillingStatus.PAID),
    Usage("23-5", "150.000", "Mei", 2023, BillingStatus.PAID),
    Usage("23-6", "145.000", "Juni", 2023, BillingStatus.PAID),
    Usage("23-7", "160.000", "Juli", 2023, BillingStatus.PAID),
    Usage("23-8", "155.000", "Agustus", 2023, BillingStatus.PAID),
    Usage("23-9", "140.000", "September", 2023, BillingStatus.PAID),
    Usage("23-10", "165.000", "Oktober", 2023, BillingStatus.PAID),
    Usage("23-11", "150.000", "November", 2023, BillingStatus.PAID),
    Usage("23-12", "170.000", "Desember", 2023, BillingStatus.PAID),
    // 2022
    Usage("22-1", "140.000", "Januari", 2022, BillingStatus.PAID),
    Usage("22-2", "145.000", "Februari", 2022, BillingStatus.PAID),
    Usage("22-3", "135.000", "Maret", 2022, BillingStatus.PAID),
    Usage("22-4", "150.000", "April", 2022, BillingStatus.PAID),
    Usage("22-5", "145.000", "Mei", 2022, BillingStatus.PAID),
    Usage("22-6", "140.000", "Juni", 2022, BillingStatus.PAID),
    Usage("22-7", "155.000", "Juli", 2022, BillingStatus.PAID),
    Usage("22-8", "150.000", "Agustus", 2022, BillingStatus.PAID),
    Usage("22-9", "135.000", "September", 2022, BillingStatus.PAID),
    Usage("22-10", "160.000", "Oktober", 2022, BillingStatus.PAID),
    Usage("22-11", "145.000", "November", 2022, BillingStatus.PAID),
    Usage("22-12", "165.000", "Desember", 2022, BillingStatus.PAID),
)
