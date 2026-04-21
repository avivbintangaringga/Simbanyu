package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Coordinate

data class Subscriber(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val meterId: String,
    val imageUrl: String,
    val coordinate: Coordinate,
    val category: String,

)

data class SubscribersUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchTextField: TextFieldState = TextFieldState(),
    val filteredSubscribers: List<Subscriber> = emptyList()
)

sealed interface SubscribersAction {
    data object Load : SubscribersAction
    data object Refresh : SubscribersAction
    data class Search(val query: String) : SubscribersAction
    data class ShowDetails(val subscriber: Subscriber) : SubscribersAction
}

sealed interface SubscribersEvent {
    data class GoToSubscriberDetails(val subscriber: Subscriber) : SubscribersEvent
}