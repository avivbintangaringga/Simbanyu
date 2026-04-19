package com.avivba.simbanyu.ui.screens.invoice

import com.avivba.simbanyu.ui.base.BaseViewModel

class InvoiceViewModel : BaseViewModel<InvoiceUiState, InvoiceAction, InvoiceEvent>(
    InvoiceUiState()
) {
    override fun onAction(action: InvoiceAction) {
        when (action) {
            is InvoiceAction.Pay -> {
                pay()
            }

            InvoiceAction.NavigateBack -> {
                
            }
            InvoiceAction.SelectPaymentMethod -> {

            }
        }
    }

    private fun pay() {

    }
}