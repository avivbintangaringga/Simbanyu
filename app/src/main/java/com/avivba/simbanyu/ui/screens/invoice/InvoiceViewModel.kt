package com.avivba.simbanyu.ui.screens.invoice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Bill
import com.avivba.simbanyu.ui.utils.toDottedString
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class InvoiceViewModel(
    private val billRepository: BillRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<InvoiceUiState, InvoiceAction, InvoiceEvent>(
    InvoiceUiState()
) {
    private val billIds = savedStateHandle.toRoute<Screen.Invoice>().ids

    init {
        viewModelScope.launch {
            load()
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

                InvoiceAction.SelectPaymentMethod -> {
                    sendEvent(InvoiceEvent.GoToPaymentMethod)
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

        fetchUserData()
        fetchBillData()

        updateState {
            it.copy(
                isLoading = false
            )
        }
    }

    private suspend fun fetchUserData() {
        when (val user = userRepository.getLocalProfile()) {
            is Result.Success -> {
                val data = user.data
                updateState {
                    it.copy(
                        name = data.name,
                        address = data.address ?: "-",
                        meterId = data.meterId ?: "-",
                        category = data.plan ?: "-"
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
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
                totalAmount = bills.sumOf { it.amount }.toDottedString(),
                bills = bills.map {
                    Bill.fromDomain(it)
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

    private fun pay() {

    }
}