package com.avivba.simbanyu.ui.screens.invoice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiBill
import com.avivba.simbanyu.ui.utils.toDottedCurrency
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class InvoiceViewModel(
    private val billRepository: BillRepository,
    private val userRepository: UserRepository,
    private val refreshEventBus: RefreshEventBus,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<InvoiceUiState, InvoiceAction, InvoiceEvent>(
    InvoiceUiState()
) {
    private val billIds = savedStateHandle.toRoute<Screen.Invoice>().ids

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
                            address = profile.address ?: "-",
                            meterId = profile.meterId ?: "-",
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

    override fun onAction(action: InvoiceAction) {
        viewModelScope.launch {
            when (action) {
                is InvoiceAction.Pay -> {
                    pay()
                }

                InvoiceAction.NavigateBack -> {
                    sendEvent(InvoiceEvent.NavigateBack)
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

    private suspend fun fetchBillData() {
        val bills = billIds.map { id ->
            viewModelScope.async {
                fetchSingleBill(id)
            }
        }.awaitAll().filterNotNull()

        updateState {
            it.copy(
                totalAmount = bills.sumOf { it.amount }.toDottedCurrency(),
                bills = bills.map {
                    UiBill.fromDomain(it)
                }
            )
        }
    }

    private suspend fun fetchSingleBill(id: String): com.avivba.simbanyu.domain.model.Bill? {
        return when (val bill = billRepository.getById(id)) {
            is Result.Success -> {
                bill.data
            }

            is Result.Error -> {
                // TODO: Handle error
                null
            }
        }
    }

    private suspend fun pay() {
        updateState {
            it.copy(
                isSending = true
            )
        }

        when (val link = billRepository.requestPaymentLink(billIds)) {
            is Result.Success -> {
                sendEvent(InvoiceEvent.OnPaymentSuccess(link.data))
                refreshEventBus.triggerRefresh()
            }

            is Result.Error -> {
                val message = "Pembayaran gagal, silahkan coba kembali"
                sendEvent(InvoiceEvent.OnPaymentError(message))
            }
        }

        updateState {
            it.copy(
                isSending = false
            )
        }
    }
}