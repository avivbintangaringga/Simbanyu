package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.collections.copy

class SubscribersViewModel(

) : BaseViewModel<SubscribersUiState, SubscribersAction, SubscribersEvent>(
    SubscribersUiState()
) {
    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: SubscribersAction) {
        viewModelScope.launch {
            when (action) {
                SubscribersAction.Load -> load()
                SubscribersAction.Refresh -> refresh()
                is SubscribersAction.ShowDetails -> {
                    sendEvent(SubscribersEvent.GoToSubscriberDetails(action.subscriber))
                }

                is SubscribersAction.SetRecordStateFilter -> {
                    updateState {
                        it.copy(
                            filterRecordState = action.filter
                        )
                    }
                }

                SubscribersAction.Search -> {
                    search()
                }

                is SubscribersAction.RecordMeter -> {
                    sendEvent(SubscribersEvent.GoToRecordMeter(action.subscriber))
                }
            }
        }
    }

    private fun load() {
        // TODO: Implement

        updateState {
            it.copy(
                filteredSubscribers = fakeSubscribers
            )
        }
    }

    private fun refresh() {
        // TODO: Implement
    }

    private fun search() {
        // TODO: Implement
    }
}
