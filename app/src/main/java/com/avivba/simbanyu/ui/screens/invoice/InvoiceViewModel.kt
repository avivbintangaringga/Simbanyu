package com.avivba.simbanyu.ui.screens.invoice

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.core.BaseViewModel
import com.avivba.simbanyu.ui.core.Screen

class InvoiceViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<InvoiceUiState, InvoiceAction, InvoiceEvent>(
    InvoiceUiState()
) {
    private val billIds = savedStateHandle.toRoute<Screen.Invoice>().ids

    init {
        updateState {
            fakeInvoiceState
        }
    }

    override fun onAction(action: InvoiceAction) {
        when (action) {
            is InvoiceAction.Pay -> {
                pay()
            }

            InvoiceAction.NavigateBack -> {
                sendEvent(InvoiceEvent.NavigateBack)
            }
            InvoiceAction.SelectPaymentMethod -> {
                sendEvent(InvoiceEvent.GoToPaymentMethod)
            }
        }
    }

    private fun pay() {

    }
}