package com.avivba.simbanyu.ui.screens.billhistory

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryEvent.PayBill
import com.avivba.simbanyu.ui.screens.billhistory.BillHistoryEvent.ShowBill
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiBill
import com.avivba.simbanyu.ui.shared.model.UiBillingStatus
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
    private val _bills = MutableStateFlow<List<UiBill>>(emptyList())

    init {
        viewModelScope.launch {
            load()
        }

        viewModelScope.launch {
            observeDataChanges()
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

    private fun changeBillSelection(bill: UiBill, selected: Boolean) {
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

    private fun clickBill(bill: UiBill) {
        if (state.value.isMultipleSelectMode) {
            changeBillSelection(bill, !bill.isSelected)
        } else {
            if (bill.status == UiBillingStatus.UNPAID) {
                sendEvent(PayBill(listOf(bill.id)))
            } else {
                sendEvent(ShowBill(bill))
            }
        }
    }

    private suspend fun payMultipleBills() {
        val selectedBillIds = _bills.value.filter { it.isSelected }.map { it.id }
        sendEvent(PayBill(selectedBillIds))
        delay(500)
        dismissMultipleSelectMode()
    }

    private fun longClickBill(bill: UiBill) {
        if (state.value.isMultipleSelectMode) {
            dismissMultipleSelectMode()
        } else {
            if (!bill.isSelected && bill.status == UiBillingStatus.UNPAID) {
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
            updateState {
                it.copy(
                    bills = filtered,
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
        when (val bills = billRepository.getAll(null)) {
            is Result.Success -> {
                val bills = bills.data.map {
                    UiBill.fromDomain(it)
                }

                val years = bills.map { it.periodYear }.distinct().sortedDescending()
                val selectedYear = years.firstOrNull() ?: 0

                _bills.update {
                    bills
                }

                updateState {
                    it.copy(
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