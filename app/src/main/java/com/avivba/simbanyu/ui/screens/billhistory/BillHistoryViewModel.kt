package com.avivba.simbanyu.ui.screens.billhistory

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter

class BillHistoryViewModel : BaseViewModel<BillHistoryUiState, BillHistoryAction, BillHistoryEvent>(
    BillHistoryUiState()
) {
    private val _bills = MutableStateFlow<List<Bill>>(emptyList())

    init {
        observeDataChanges()
        load()
    }

    override fun onAction(action: BillHistoryAction) {
        when (action) {
            BillHistoryAction.Load -> load()
            BillHistoryAction.Refresh -> refresh()
            is BillHistoryAction.Filter -> filter(action.year)
            is BillHistoryAction.ClickBill -> sendEvent(BillHistoryEvent.ShowBill(action.bill))
        }
    }

    private fun observeDataChanges() {
        viewModelScope.launch {
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
    }

    private fun refresh() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isRefreshing = true
                )
            }
            // TODO: Reload data
            delay(3000)
            updateState {
                it.copy(
                    isRefreshing = false
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

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true
                )
            }

            delay(1000)

            // TODO: Implement load data
            val newData = fakeBills
            _bills.update { newData }

            val years = newData.map { it.periodYear }.distinct().sortedDescending()
            val selectedYear = years.firstOrNull() ?: 0

            updateState {
                it.copy(
                    years = years,
                    selectedYear = selectedYear,
                    isLoading = false
                )
            }
        }
    }
}