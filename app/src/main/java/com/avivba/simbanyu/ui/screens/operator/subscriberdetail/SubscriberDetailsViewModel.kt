package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Bill
import com.avivba.simbanyu.ui.shared.model.Usage
import com.avivba.simbanyu.ui.theme.SimbanyuTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SubscriberDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SubscriberDetailsUiState, SubscriberDetailsAction, SubscriberDetailsEvent>(
    SubscriberDetailsUiState()
) {
    val subscriberId = savedStateHandle.toRoute<Screen.SubscriberDetails>().id
    private val _usages = MutableStateFlow<List<Bill>>(emptyList())
    private val _bills = MutableStateFlow<List<Usage>>(emptyList())

    init {
        load()
    }

    override fun onAction(action: SubscriberDetailsAction) {
        viewModelScope.launch {
            when (action) {
                SubscriberDetailsAction.Load -> load()
                SubscriberDetailsAction.Refresh -> refresh()
                SubscriberDetailsAction.NavigateBack -> {
                    sendEvent(SubscriberDetailsEvent.NavigateBack)
                }

                is SubscriberDetailsAction.ChangeTab -> {
                    updateState {
                        it.copy(
                            selectedTabIndex = action.index
                        )
                    }
                }

                is SubscriberDetailsAction.FilterUsage -> {
                    updateState {
                        it.copy(
                            usageSelectedYear = action.year
                        )
                    }
                }

                SubscriberDetailsAction.RecordMeter -> {
                    sendEvent(SubscriberDetailsEvent.GoToRecordMeter)
                }

                SubscriberDetailsAction.ContactViaWhatsApp -> {
                    sendEvent(SubscriberDetailsEvent.GoToWhatsApp)
                }
            }
        }
    }

    private fun load() {
        updateState {
            fakeSubscriberDetailsUiState
        }
    }

    private fun refresh() {
        // TODO: Implement
    }
}

