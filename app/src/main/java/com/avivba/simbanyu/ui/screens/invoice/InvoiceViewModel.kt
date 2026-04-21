package com.avivba.simbanyu.ui.screens.invoice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.Screen
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
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
    }

    private fun pay() {

    }
}