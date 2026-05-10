package com.avivba.simbanyu.ui.screens.billdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.BillingStatus
import com.avivba.simbanyu.ui.shared.model.Cost
import com.avivba.simbanyu.ui.utils.toDottedString
import kotlinx.coroutines.launch

class BillDetailsViewModel(
    private val billRepository: BillRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BillDetailsUiState, BillDetailsAction, BillDetailsEvent>(
    BillDetailsUiState()
) {
    private val id = savedStateHandle.toRoute<Screen.BillDetails>().id

    init {
        viewModelScope.launch {
            load()
        }
    }

    override fun onAction(action: BillDetailsAction) {
        viewModelScope.launch {
            when (action) {
                BillDetailsAction.DownloadReceipt -> downloadReceipt()
                BillDetailsAction.NavigateBack -> sendEvent(BillDetailsEvent.NavigateBack)
                BillDetailsAction.Load -> load()
                is BillDetailsAction.OpenPaymentPage -> {
                    sendEvent(BillDetailsEvent.OpenPaymentPage(action.url))
                }
            }
        }
    }

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isLoading = true
            )
        }
    }

    private suspend fun fetchData() {
        when (val bill = billRepository.getById(id)) {
            is Result.Success -> {
                val bill = bill.data
                updateState {
                    it.copy(
                        details = bill.items.map {
                            Cost(
                                name = it.name,
                                amount = it.amount.toDottedString()
                            )
                        },
                        status = BillingStatus.valueOf(bill.status.name),
                        totalAmount = bill.amount.toDottedString(),
                        payments = bill.payments
                            .sortedByDescending { it.date }
                            .map {
                                UiPayment.fromDomain(it)
                            }
                    )
                }
            }

            is Result.Error -> {

            }
        }
    }

    private fun downloadReceipt() {
        // TODO: Implement download receipt
    }
}