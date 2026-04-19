package com.avivba.simbanyu.ui.screens.invoice

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.base.BaseViewModel
import com.avivba.simbanyu.ui.screens.main.Screen

class InvoiceViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<InvoiceUiState, InvoiceAction, InvoiceEvent>(
    InvoiceUiState()
) {
    private val billIds = savedStateHandle.toRoute<Screen.Invoice>().ids

    init {
        Log.d("InvoiceViewModel", "BillIds: $billIds")
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