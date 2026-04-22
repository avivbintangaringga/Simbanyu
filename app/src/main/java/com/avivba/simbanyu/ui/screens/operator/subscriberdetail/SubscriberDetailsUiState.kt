package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

data class SubscriberDetailsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

sealed interface SubscriberDetailsAction {
    data object Load : SubscriberDetailsAction
    data object Refresh : SubscriberDetailsAction
    data object NavigateBack : SubscriberDetailsAction
}

sealed interface SubscriberDetailsEvent {
    data object NavigateBack : SubscriberDetailsEvent
}