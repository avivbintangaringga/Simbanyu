package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import com.avivba.simbanyu.ui.screens.billhistory.fakeBills
import com.avivba.simbanyu.ui.screens.usagehistory.fakeUsages
import com.avivba.simbanyu.ui.shared.model.UiBill
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.shared.model.UiUsage

data class SubscriberDetailsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val phone: String = "",
    val category: String = "",
    val location: UiCoordinate? = null,
    val isRecorded: Boolean = false,

    val selectedTabIndex: Int = 0,

    val usageSelectedYear: Int = 0,
    val usageYears: List<Int> = emptyList(),
    val usageTotal: Int = 0,
    val usageAverage: Double = 0.0,
    val usages: List<UiUsage> = emptyList(),

    val billSelectedYear: Int = 0,
    val billYears: List<Int> = emptyList(),
    val bills: List<UiBill> = emptyList(),
)

sealed interface SubscriberDetailsAction {
    data object Load : SubscriberDetailsAction
    data object Refresh : SubscriberDetailsAction
    data object NavigateBack : SubscriberDetailsAction
    data class ChangeTab(val index: Int) : SubscriberDetailsAction
    data class FilterBill(val year: Int) : SubscriberDetailsAction
    data class FilterUsage(val year: Int) : SubscriberDetailsAction
    data object RecordMeter : SubscriberDetailsAction
    data object UpdateMeter : SubscriberDetailsAction
    data object ContactViaWhatsApp : SubscriberDetailsAction
    data class PayByCash(val bill: UiBill) : SubscriberDetailsAction
    data class ClickUsage(val usage: UiUsage) : SubscriberDetailsAction
}

sealed interface SubscriberDetailsEvent {
    data object NavigateBack : SubscriberDetailsEvent
    data class GoToRecordMeter(val editMode: Boolean) : SubscriberDetailsEvent
    data object GoToWhatsApp : SubscriberDetailsEvent
}

val fakeSubscriberDetailsUiState = SubscriberDetailsUiState(
    name = "Budi Santoso",
    address = "Jl. Merdeka No. 10, Jakarta",
    meterId = "MTR001",
    category = "Rumah Tangga A",
    phone = "082212232131",
    location = UiCoordinate(
        -7.911178168148756,
        110.69031351790268
    ),

    usageSelectedYear = 2024,
    usageYears = listOf(2023, 2024, 2025, 2026),
    usageTotal = 11312,
    usageAverage = 1412.2,
    usages = fakeUsages,

    billSelectedYear = 2024,
    billYears = listOf(2023, 2024, 2025, 2026),
    bills = fakeBills
)