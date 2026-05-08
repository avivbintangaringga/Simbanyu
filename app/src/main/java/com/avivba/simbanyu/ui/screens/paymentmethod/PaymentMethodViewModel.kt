package com.avivba.simbanyu.ui.screens.paymentmethod

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class PaymentMethodViewModel : BaseViewModel<PaymentMethodUiState, PaymentMethodUiAction, PaymentMethodUiEvent>(
    PaymentMethodUiState()
) {

    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: PaymentMethodUiAction) {
        viewModelScope.launch {
            when (action) {
                is PaymentMethodUiAction.SelectPaymentMethod -> {
                    updateState {
                        it.copy(
                            selectedPaymentMethod = action.paymentMethod
                        )
                    }
                }

                is PaymentMethodUiAction.Choose -> {
                    // TODO: Handle selected payment method
                    sendEvent(PaymentMethodUiEvent.GoBack)
                }

                PaymentMethodUiAction.GoBack -> {
                    sendEvent(PaymentMethodUiEvent.GoBack)
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true
                )
            }

            updateState {
                it.copy(
                    eWallet = fakeEWallet,
                    bankTransfer = fakeBankTransfer,
                    retail = fakeRetail
                )
            }

            updateState {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}