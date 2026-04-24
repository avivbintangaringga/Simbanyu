package com.avivba.simbanyu.ui.screens.usagehistory

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Usage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsageHistoryViewModel :
    BaseViewModel<UsageHistoryUiState, UsageHistoryAction, UsageHistoryEvent>(
        UsageHistoryUiState()
    ) {
    private val _usages = MutableStateFlow<List<Usage>>(emptyList())

    init {
        viewModelScope.launch {
            observeDataChanges()
            load()
        }
    }

    override fun onAction(action: UsageHistoryAction) {
        viewModelScope.launch {
            when (action) {
                UsageHistoryAction.Load -> load()
                UsageHistoryAction.Refresh -> refresh()
                is UsageHistoryAction.Filter -> filter(action.year)
                is UsageHistoryAction.ClickUsage -> {
                    toggleItemExpanded(action.usage)
                }
            }
        }
    }

    private fun toggleItemExpanded(usage: Usage) {
        updateState {
            it.copy(
                usages = it.usages.map { item ->
                    if (item.id == usage.id) {
                        item.copy(
                            isExpanded = !item.isExpanded
                        )
                    } else {
                        item
                    }
                }
            )
        }
    }

    private fun observeDataChanges() {
        viewModelScope.launch {
            combine(
                _usages,
                state.map { it.selectedYear }.distinctUntilChanged()
            ) { usages, year ->
                usages.filter { it.periodYear == year }
            }.collect { filtered ->
                val usageTotal = filtered.sumOf { it.amount }
                val usageAverage = filtered.map {
                    it.amount
                }.average().takeUnless {
                    it.isNaN()
                } ?: 0.0

                Log.d("UsageHistoryViewModel", "observeDataChanges: $filtered")
                Log.d("UsageHistoryViewModel", "observeDataChanges: $usageTotal")
                Log.d("UsageHistoryViewModel", "observeDataChanges: $usageAverage")


                updateState {
                    it.copy(
                        usages = filtered,
                        usageTotal = usageTotal,
                        usageAverage = usageAverage
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

            // TODO: Implement load data
            val newData = fakeUsages
            _usages.update { newData }

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