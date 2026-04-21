package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.screens.operator.subsribers.Subscriber

data class SubscriberDetailsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchTextField: TextFieldState = TextFieldState(),
    val filteredSubscribers: List<Subscriber> = emptyList()
)

sealed interface SubscriberDetailsAction {
    data object Load : SubscriberDetailsAction
    data object Refresh : SubscriberDetailsAction
    data object NavigateBack : SubscriberDetailsAction
}

sealed interface SubscriberDetailsEvent {
    data object NavigateBack : SubscriberDetailsEvent
}
