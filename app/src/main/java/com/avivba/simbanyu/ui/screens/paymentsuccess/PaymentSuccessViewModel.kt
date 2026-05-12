package com.avivba.simbanyu.ui.screens.paymentsuccess

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class PaymentSuccessViewModel(
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<PaymentSuccessUiState, PaymentSuccessAction, PaymentSuccessEvent>(
    PaymentSuccessUiState()
) {
    override fun onAction(action: PaymentSuccessAction) {
        viewModelScope.launch {
            when (action) {
                PaymentSuccessAction.NavigateBack -> {
                    refreshEventBus.triggerRefresh()
                    sendEvent(PaymentSuccessEvent.NavigateBack)
                }
            }
        }
    }
}
