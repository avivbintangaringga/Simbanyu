package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.operator.subsribers.SubscribersEvent.*
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class SubscribersViewModel(

) : BaseViewModel<SubscribersUiState, SubscribersAction, SubscribersEvent>(
    SubscribersUiState()
) {
    override fun onAction(action: SubscribersAction) {
        viewModelScope.launch {
            when (action) {
                SubscribersAction.Load -> load()
                SubscribersAction.Refresh -> refresh()
                is SubscribersAction.ShowDetails -> {
                    sendEvent(GoToSubscriberDetails(action.subscriber))
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

private fun search(query: String) {
    // TODO: Implement
}