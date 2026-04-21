package com.avivba.simbanyu.ui.screens.billdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.avivba.simbanyu.ui.base.BaseViewModel
import com.avivba.simbanyu.ui.screens.main.Screen
import kotlinx.coroutines.launch

class BillDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BillDetailsUiState, BillDetailsAction, BillDetailsEvent>(
    BillDetailsUiState()
) {
    private val id = savedStateHandle.toRoute<Screen.BillDetails>().id

    init {
        load()
    }

    override fun onAction(action: BillDetailsAction) {
        viewModelScope.launch {
            when(action) {
                BillDetailsAction.DownloadReceipt -> downloadReceipt()
                BillDetailsAction.NavigateBack -> sendEvent(BillDetailsEvent.NavigateBack)
                BillDetailsAction.OpenPaymentPage -> {

                }
            }
        }
    }

    private fun load() {
        updateState {
            fakeBillDetailsUiState
        }
    }

    private fun downloadReceipt() {
        // TODO: Implement download receipt
    }
}