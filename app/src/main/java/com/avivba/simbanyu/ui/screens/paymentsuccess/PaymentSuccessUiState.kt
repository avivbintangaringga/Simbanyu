package com.avivba.simbanyu.ui.screens.paymentsuccess

data class PaymentSuccessUiState(
    val isLoading: Boolean = false
)

sealed interface PaymentSuccessAction {
    data object NavigateBack : PaymentSuccessAction
}

sealed interface PaymentSuccessEvent {
    data object NavigateBack : PaymentSuccessEvent
}
