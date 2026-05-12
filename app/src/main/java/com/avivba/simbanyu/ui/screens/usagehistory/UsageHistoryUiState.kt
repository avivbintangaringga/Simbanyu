package com.avivba.simbanyu.ui.screens.usagehistory

import com.avivba.simbanyu.ui.shared.model.UiUsage

data class UsageHistoryUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val years: List<Int> = emptyList(),
    val selectedYear: Int = 0,
    val usageTotal: Int = 0,
    val usageAverage: Double = 0.0,
    val usages: List<UiUsage> = emptyList()
)

sealed interface UsageHistoryAction {
    data object Load : UsageHistoryAction
    data object Refresh : UsageHistoryAction
    data class Filter(val year: Int) : UsageHistoryAction
    data class ClickUsage(val usage: UiUsage) : UsageHistoryAction
}

sealed interface UsageHistoryEvent

val fakeUsages = listOf(
    // 2024
    UiUsage("24-1", 100, 120, 50, "Januari", 2024),
    UiUsage("24-2", 120, 145, 25, "Februari", 2024),
    UiUsage("24-3", 145, 163, 18, "Maret", 2024),
    UiUsage("24-4", 163, 185, 22, "April", 2024),
    UiUsage("24-5", 185, 210, 25, "Mei", 2024),
    UiUsage("24-6", 210, 230, 20, "Juni", 2024),
    UiUsage("24-7", 230, 255, 25, "Juli", 2024),
    UiUsage("24-8", 255, 271, 16, "Agustus", 2024),
    UiUsage("24-9", 271, 290, 19, "September", 2024),
    UiUsage("24-10", 290, 314, 24, "Oktober", 2024),
    UiUsage("24-11", 314, 335, 21, "November", 2024),
    UiUsage("24-12", 335, 360, 25, "Desember", 2024),
    // 2023
    UiUsage("23-1", 400, 422, 22, "Januari", 2023),
    UiUsage("23-2", 422, 440, 18, "Februari", 2023),
    UiUsage("23-3", 440, 465, 25, "Maret", 2023),
    UiUsage("23-4", 465, 485, 20, "April", 2023),
    UiUsage("23-5", 485, 508, 23, "Mei", 2023),
    UiUsage("23-6", 508, 528, 40, "Juni", 2023),
    UiUsage("23-7", 528, 550, 22, "Juli", 2023),
    UiUsage("23-8", 550, 575, 25, "Agustus", 2023),
    UiUsage("23-9", 575, 592, 17, "September", 2023),
    UiUsage("23-10", 592, 615, 23, "Oktober", 2023),
    UiUsage("23-11", 615, 636, 21, "November", 2023),
    UiUsage("23-12", 636, 660, 24, "Desember", 2023),
    // 2022
    UiUsage("22-1", 700, 720, 20, "Januari", 2022),
    UiUsage("22-2", 720, 742, 22, "Februari", 2022),
    UiUsage("22-3", 742, 760, 18, "Maret", 2022),
    UiUsage("22-4", 760, 785, 25, "April", 2022),
    UiUsage("22-5", 785, 805, 20, "Mei", 2022),
    UiUsage("22-6", 805, 828, 23, "Juni", 2022),
    UiUsage("22-7", 828, 850, 22, "Juli", 2022),
    UiUsage("22-8", 850, 875, 25, "Agustus", 2022),
    UiUsage("22-9", 875, 892, 17, "September", 2022),
    UiUsage("22-10", 892, 915, 23, "Oktober", 2022),
    UiUsage("22-11", 915, 936, 21, "November", 2022),
    UiUsage("22-12", 936, 960, 24, "Desember", 2022),
)
