package com.avivba.simbanyu.ui.screens.billdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.screens.billdetails.BillDetailsEvent.OpenPaymentPage
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiBill
import kotlinx.coroutines.launch

class BillDetailsViewModel(
    private val billRepository: BillRepository,
    private val userRepository: UserRepository,
    private val refreshEventBus: RefreshEventBus,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BillDetailsUiState, BillDetailsAction, BillDetailsEvent>(
    BillDetailsUiState()
) {
    private val id = savedStateHandle.toRoute<Screen.BillDetails>().id

    init {
        viewModelScope.launch {
            load()
        }

        viewModelScope.launch {
            userRepository.getLocalProfileFlow().collect { profile ->
                if (profile != null) {
                    updateState {
                        it.copy(
                            name = profile.name,
                            meterId = profile.meterId ?: "-",
                            address = profile.address ?: "-",
                            category = profile.plan ?: "-"
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                load()
            }
        }
    }

    override fun onAction(action: BillDetailsAction) {
        viewModelScope.launch {
            when (action) {
                BillDetailsAction.DownloadReceipt -> downloadReceipt()
                BillDetailsAction.NavigateBack -> sendEvent(BillDetailsEvent.NavigateBack)
                BillDetailsAction.Load -> load()
                BillDetailsAction.Refresh -> refresh()
                BillDetailsAction.CheckBillStatus -> checkBillStatus()
                is BillDetailsAction.OpenPaymentPage -> {
                    sendEvent(OpenPaymentPage(action.url))
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

        fetchBillData()

        updateState {
            it.copy(
                isLoading = false
            )
        }
    }

    private suspend fun checkBillStatus() {
        fetchBillData()
    }

    private suspend fun refresh() {
        updateState {
            it.copy(
                isRefreshing = true
            )
        }

        fetchBillData()

        updateState {
            it.copy(
                isRefreshing = false
            )
        }
    }

    private suspend fun fetchBillData() {
        when (val bill = billRepository.getById(id)) {
            is Result.Success -> {
                val bill = UiBill.fromDomain(bill.data)
                updateState {
                    it.copy(
                        period = "${bill.periodMonth} ${bill.periodYear}",
                        details = bill.costs,
                        status = bill.status,
                        totalAmount = bill.amount,
                        payments = bill.payments
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