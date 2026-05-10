package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiBill
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.shared.model.UiUsage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriberDetailsViewModel(
    private val subscriberRepository: SubscriberRepository,
    private val usageRepository: UsageRepository,
    private val billRepository: BillRepository,
    private val refreshEventBus: RefreshEventBus,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SubscriberDetailsUiState, SubscriberDetailsAction, SubscriberDetailsEvent>(
    SubscriberDetailsUiState()
) {
    val subscriberId = savedStateHandle.toRoute<Screen.SubscriberDetails>().id
    private val _usages = MutableStateFlow<List<UiUsage>>(emptyList())
    private val _bills = MutableStateFlow<List<UiBill>>(emptyList())

    init {
        viewModelScope.launch {
            load()
        }

        viewModelScope.launch {
            observeUsageChanges()
        }

        viewModelScope.launch {
            observeBillChanges()
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                load()
            }
        }
    }

    override fun onAction(action: SubscriberDetailsAction) {
        viewModelScope.launch {
            when (action) {
                SubscriberDetailsAction.Load -> load()
                SubscriberDetailsAction.Refresh -> refresh()
                SubscriberDetailsAction.NavigateBack -> {
                    sendEvent(SubscriberDetailsEvent.NavigateBack)
                }

                is SubscriberDetailsAction.ChangeTab -> {
                    updateState {
                        it.copy(
                            selectedTabIndex = action.index
                        )
                    }
                }

                is SubscriberDetailsAction.FilterUsage -> {
                    updateState {
                        it.copy(
                            usageSelectedYear = action.year
                        )
                    }
                }

                is SubscriberDetailsAction.FilterBill -> {
                    updateState {
                        it.copy(
                            billSelectedYear = action.year
                        )
                    }
                }

                SubscriberDetailsAction.RecordMeter -> {
                    sendEvent(SubscriberDetailsEvent.GoToRecordMeter(false))
                }

                SubscriberDetailsAction.ContactViaWhatsApp -> {
                    sendEvent(SubscriberDetailsEvent.GoToWhatsApp)
                }

                is SubscriberDetailsAction.PayByCash -> {
                    payByCash(action.bill.id)
                }

                is SubscriberDetailsAction.ClickUsage -> {
                    toggleUsageExpanded(action.usage)
                }

                SubscriberDetailsAction.UpdateMeter -> {
                    sendEvent(SubscriberDetailsEvent.GoToRecordMeter(true))
                }
            }
        }
    }

    private fun toggleUsageExpanded(usage: UiUsage) {
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

    private suspend fun fetchData() {
        listOf(
            viewModelScope.async {
                when (val subscriber = subscriberRepository.getById(subscriberId)) {
                    is Result.Success -> {
                        val data = subscriber.data
                        updateState {
                            it.copy(
                                name = data.user.name,
                                address = data.address,
                                meterId = data.meterId,
                                phone = data.user.phoneNumber,
                                category = data.plan,
                                location = UiCoordinate.fromDomain(
                                    data.location
                                ),
                                isRecorded = data.isRecorded,
                            )
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            },
            viewModelScope.async {
                when (val usages = usageRepository.getAll(subscriberId)) {
                    is Result.Success -> {
                        val usages = usages.data.map {
                            UiUsage.fromDomain(it)
                        }

                        val years = usages.map { it.periodYear }.distinct().sortedDescending()
                        val selectedYear = years.firstOrNull() ?: 0

                        _usages.update { usages }

                        updateState {
                            it.copy(
                                usageYears = years,
                                usageSelectedYear = selectedYear,
                            )
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            },
            viewModelScope.async {
                when (val bills = billRepository.getAll(subscriberId)) {
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
                                billYears = years,
                                billSelectedYear = selectedYear,
                            )
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            }
        ).awaitAll()
    }

    private suspend fun payByCash(id: String) {
        if (state.value.isSending) return

        updateState {
            it.copy(
                isSending = true
            )
        }

        when (billRepository.payByCash(listOf(id))) {
            is Result.Success -> {
                sendEvent(
                    SubscriberDetailsEvent.OnCashPaymentResult(
                        "Pembayaran berhasil"
                    )
                )

                refreshEventBus.triggerRefresh()
            }

            is Result.Error -> {
                // TODO: Handle error
                sendEvent(
                    SubscriberDetailsEvent.OnCashPaymentResult(
                        "Pembayaran gagal, coba lagi nanti"
                    )
                )
            }
        }

        updateState {
            it.copy(
                isSending = false
            )
        }
    }

    private suspend fun observeUsageChanges() {
        combine(
            _usages,
            state.map { it.usageSelectedYear }.distinctUntilChanged()
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

    private suspend fun observeBillChanges() {
        combine(
            _bills,
            state.map { it.billSelectedYear }.distinctUntilChanged()
        ) { bills, year ->
            Log.d("SDKSDK", "BILL YEAR: $year")
            bills.filter { it.periodYear == year }
        }.collect { filtered ->
            updateState {
                it.copy(
                    bills = filtered,
                )
            }
        }
    }
}

