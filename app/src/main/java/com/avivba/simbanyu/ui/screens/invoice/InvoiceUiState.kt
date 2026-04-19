package com.avivba.simbanyu.ui.screens.invoice

data class InvoiceUiState(
    val totalAmount: String = ""
)

sealed interface InvoiceAction {
    data object NavigateBack : InvoiceAction
    data object Pay : InvoiceAction
    data object SelectPaymentMethod : InvoiceAction
}

sealed interface InvoiceEvent {
    data object GoToPaymentMethod : InvoiceEvent
}