package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import com.avivba.simbanyu.ui.screens.usagehistory.fakeUsages
import com.avivba.simbanyu.ui.shared.model.Usage

data class SubscriberDetailsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val phone: String = "",
    val category: String = "",
    val isRecorded: Boolean = false,

    val selectedTabIndex: Int = 0,

    val usageSelectedYear: Int = 0,
    val usageYears: List<Int> = emptyList(),
    val usageTotal: Int = 0,
    val usageAverage: Double = 0.0,
    val usages: List<Usage> = emptyList()
)

sealed interface SubscriberDetailsAction {
    data object Load : SubscriberDetailsAction
    data object Refresh : SubscriberDetailsAction
    data object NavigateBack : SubscriberDetailsAction
    data class ChangeTab(val index: Int) : SubscriberDetailsAction
    data class FilterUsage(val year: Int) : SubscriberDetailsAction
    data object RecordMeter : SubscriberDetailsAction
}

sealed interface SubscriberDetailsEvent {
    data object NavigateBack : SubscriberDetailsEvent
    data class GoToRecordMeter(val subscriberId: String) : SubscriberDetailsEvent
}

val fakeSubscriberDetailsUiState = SubscriberDetailsUiState(
    name = "Budi Santoso",
    address = "Jl. Merdeka No. 10, Jakarta",
    meterId = "MTR001",
    category = "Rumah Tangga A",
    phone = "082212232131",

    usageTotal = 11312,
    usageAverage = 1412.2,
    usages = fakeUsages,
)