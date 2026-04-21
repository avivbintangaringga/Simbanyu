package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersAction
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersEvent
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersEvent.*
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersUiState
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class SubscriberDetailsViewModel(

) : BaseViewModel<SubscriberDetailsUiState, SubscriberDetailsAction, SubscriberDetailsEvent>(
    SubscriberDetailsUiState()
) {
    override fun onAction(action: SubscriberDetailsAction) {
        viewModelScope.launch {
            when (action) {
                SubscriberDetailsAction.Load -> load()
                SubscriberDetailsAction.Refresh -> refresh()
                SubscriberDetailsAction.NavigateBack -> {
                    sendEvent(SubscriberDetailsEvent.NavigateBack)
                }
            }
        }
    }
}

private fun load() {
    // TODO: Implement
}

private fun refresh() {
    // TODO: Implement
}
