package com.avivba.simbanyu.ui.screens.usagehistory

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Usage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsageHistoryViewModel(
    private val usageRepository: UsageRepository,
    private val userRepository: UserRepository
) : BaseViewModel<UsageHistoryUiState, UsageHistoryAction, UsageHistoryEvent>(
    UsageHistoryUiState()
) {
    private val _usages = MutableStateFlow<List<Usage>>(emptyList())

    init {
        viewModelScope.launch {
            load()
        }

        viewModelScope.launch {
            observeDataChanges()
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

    private suspend fun observeDataChanges() {
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

            updateState {
                it.copy(
                    usages = filtered,
                    usageTotal = usageTotal,
                    usageAverage = usageAverage
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
        viewModelScope.launch {
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
    }

    private suspend fun fetchData() {
        when (val usage = usageRepository.getAll(null)) {
            is Result.Success -> {
                val usages = usage.data.map {
                    Usage.fromDomain(it)
                }

                val years = usages.map { it.periodYear }.distinct().sortedDescending()
                val selectedYear = years.firstOrNull() ?: 0

                _usages.update { usages }

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