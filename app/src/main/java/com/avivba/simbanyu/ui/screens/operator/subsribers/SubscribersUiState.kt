package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Coordinate

enum class FilterRecordState(
    val title: String
) {
    ALL("Semua"),
    NOT_RECORDED("Belum Dicatat"),
    RECORDED("Selesai"),
}

data class Subscriber(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val coordinate: Coordinate,
    val category: String,
    val meterId: String,
    val lastReading: String,
    val isRecorded: Boolean,
    val imageUrl: String,
)

data class SubscribersUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchTextField: TextFieldState = TextFieldState(),
    val filteredSubscribers: List<Subscriber> = emptyList(),
    val filterRecordState: FilterRecordState = FilterRecordState.ALL,
    val searchBarState: TextFieldState = TextFieldState()
)

sealed interface SubscribersAction {
    data object Load : SubscribersAction
    data object Refresh : SubscribersAction
    data object Search : SubscribersAction
    data class ShowDetails(val subscriber: Subscriber) : SubscribersAction
    data class SetRecordStateFilter(val filter: FilterRecordState) : SubscribersAction
}

sealed interface SubscribersEvent {
    data class GoToSubscriberDetails(val subscriber: Subscriber) : SubscribersEvent
}