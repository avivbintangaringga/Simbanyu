package com.avivba.simbanyu.ui.screens.billhistory

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryEvent.PayBill
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryEvent.ShowBill
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Bill
import com.avivba.simbanyu.ui.shared.model.BillingStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BillHistoryViewModel(
    private val billRepository: BillRepository,
) : BaseViewModel<BillHistoryUiState, BillHistoryAction, BillHistoryEvent>(
    BillHistoryUiState()
) {
    private val _bills = MutableStateFlow<List<Bill>>(emptyList())

    init {
        viewModelScope.launch {
            observeDataChanges()
            load()
        }
    }

    override fun onAction(action: BillHistoryAction) {
        viewModelScope.launch {
            when (action) {
                BillHistoryAction.Load -> load()
                BillHistoryAction.Refresh -> refresh()
                is BillHistoryAction.Filter -> filter(action.year)
                BillHistoryAction.ActivateMultipleSelectMode -> {
                    updateState {
                        it.copy(
                            isMultipleSelectMode = true
                        )
                    }
                }

                is BillHistoryAction.ClickBill -> clickBill(action.bill)
                is BillHistoryAction.LongClickBill -> longClickBill(action.bill)
                is BillHistoryAction.PayMultipleBills -> payMultipleBills()
            }
        }
    }

    private fun dismissMultipleSelectMode() {
        _bills.update {
            it.map { bill ->
                bill.copy(
                    isSelected = false
                )
            }
        }

        updateState {
            it.copy(
                isMultipleSelectMode = false
            )
        }
    }

    private fun changeBillSelection(bill: Bill, selected: Boolean) {
        _bills.update {
            it.map { b ->
                if (b.id == bill.id) b.copy(isSelected = selected) else b
            }
        }

        val anySelected = _bills.value.any { it.isSelected }

        updateState {
            it.copy(
                isMultipleSelectMode = anySelected,
                totalSelectedBills = calculateTotalSelectedBills()
            )
        }
    }

    private fun calculateTotalSelectedBills(): Double {
        val selectedBills = _bills.value.filter { it.isSelected }
        return selectedBills.sumOf { it.amount.toDouble() }
    }

    private fun clickBill(bill: Bill) {
        if (state.value.isMultipleSelectMode) {
            changeBillSelection(bill, !bill.isSelected)
        } else {
            if (bill.status == BillingStatus.PAID) {
                sendEvent(ShowBill(bill))
            } else {
                sendEvent(PayBill(listOf(bill.id)))
            }
        }
    }

    private suspend fun payMultipleBills() {
        val selectedBillIds = _bills.value.filter { it.isSelected }.map { it.id }
        sendEvent(PayBill(selectedBillIds))
        delay(500)
        dismissMultipleSelectMode()
    }

    private fun longClickBill(bill: Bill) {
        if (state.value.isMultipleSelectMode) {
            dismissMultipleSelectMode()
        } else {
            if (!bill.isSelected && bill.status == BillingStatus.UNPAID) {
                changeBillSelection(bill, true)
            }
        }
    }

    private suspend fun observeDataChanges() {
        combine(
            _bills,
            state.map { it.selectedYear }.distinctUntilChanged()
        ) { bills, year ->
            bills.filter { it.periodYear == year }
        }.collect { filtered ->
            // TODO: Implement real total bill calculation
            val totalPaid = filtered.filter { it.status == BillingStatus.PAID }
                .sumOf { it.amount.toDouble() }
            val totalUnpaid = filtered.filter { it.status == BillingStatus.UNPAID }
                .sumOf { it.amount.toDouble() }

            updateState {
                it.copy(
                    bills = filtered,
                    totalPaid = totalPaid.toString(),
                    totalUnpaid = totalUnpaid.toString()
                )
            }
        }
    }

    private fun filter(year: Int) {
        updateState {
            it.copy(
                selectedYear = year
            )
        }
    }

    private suspend fun refresh() {
        updateState {
            it.copy(
                isRefreshing = true
            )
        }

        fetchData()

        updateState {
            it.copy(
                isRefreshing = false
            )
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
                isLoading = false
            )
        }
    }

    private suspend fun fetchData() {
        when (val bills = billRepository.getAll()) {
            is Result.Success -> {
                val bills = bills.data.map {
                    Bill.fromDomain(it)
                }

                val years = bills.map { it.periodYear }.distinct().sortedDescending()
                val selectedYear = years.firstOrNull() ?: 0

                updateState {
                    it.copy(
                        bills = bills,
                        years = years,
                        selectedYear = selectedYear,
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}