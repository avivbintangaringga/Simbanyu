package com.avivba.simbanyu.ui.screens.operator.subscriberdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Bill
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.shared.model.Usage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriberDetailsViewModel(
    private val subscriberRepository: SubscriberRepository,
    private val usageRepository: UsageRepository,
    private val billRepository: BillRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SubscriberDetailsUiState, SubscriberDetailsAction, SubscriberDetailsEvent>(
    SubscriberDetailsUiState()
) {
    val subscriberId = savedStateHandle.toRoute<Screen.SubscriberDetails>().id
    private val _usages = MutableStateFlow<List<Usage>>(emptyList())
    private val _bills = MutableStateFlow<List<Bill>>(emptyList())

    init {
        viewModelScope.launch {
            load()
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

                SubscriberDetailsAction.RecordMeter -> {
                    sendEvent(SubscriberDetailsEvent.GoToRecordMeter)
                }

                SubscriberDetailsAction.ContactViaWhatsApp -> {
                    sendEvent(SubscriberDetailsEvent.GoToWhatsApp)
                }

                is SubscriberDetailsAction.PayByCash -> {
                    // TODO: Handle pay by cash
                }

                is SubscriberDetailsAction.ClickUsage -> {
                    toggleUsageExpanded(action.usage)
                }
            }
        }
    }

    private fun toggleUsageExpanded(usage: Usage) {
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
                        location = Coordinate.fromDomain(
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

        when (val usages = usageRepository.getAll(subscriberId)) {
            is Result.Success -> {
                _usages.update {
                    usages.data.map {
                        Usage.fromDomain(it)
                    }
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }

        when (val bills = billRepository.getAll(subscriberId)) {
            is Result.Success -> {
                _bills.update {
                    bills.data.map {
                        Bill.fromDomain(it)
                    }
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}

