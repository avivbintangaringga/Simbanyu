package com.avivba.simbanyu.ui.screens.dashboard

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiAnnouncement
import com.avivba.simbanyu.ui.utils.toDottedCurrency
import com.avivba.simbanyu.ui.utils.toMonthShort
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val billRepository: BillRepository,
    private val announcementRepository: AnnouncementRepository,
    private val usageRepository: UsageRepository,
    private val userRepository: UserRepository
) : BaseViewModel<DashboardUiState, DashboardAction, DashboardEvent>(
    DashboardUiState()
) {
    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    override fun onAction(action: DashboardAction) {
        viewModelScope.launch {
            when (action) {
                DashboardAction.Load -> load()
                DashboardAction.PayBill -> {
                    sendEvent(DashboardEvent.GoToInvoice)
                }

                DashboardAction.ViewAllAnnouncements -> sendEvent(DashboardEvent.ViewAllAnnouncements)
                DashboardAction.Refresh -> refresh()
            }
        }
    }

    private suspend fun refresh() {
        updateState {
            it.copy(
                isRefreshing = true,
            )
        }

        fetchData()

        updateState {
            it.copy(
                isRefreshing = false,
            )
        }
    }

    private suspend fun load() {
        updateState {
            it.copy(
                isLoading = true,
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
        listOf(
            viewModelScope.async {
                when (val profile = userRepository.getLocalProfile()) {
                    is Result.Success -> {
                        updateState {
                            it.copy(
                                name = profile.data.name
                            )
                        }
                        val subscriberId = profile.data.subscriberId
                        if (subscriberId != null) {
                            loadUsages(subscriberId)
                        } else {
                            // TODO: Handle error
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            },
            viewModelScope.async {
                when (val activeBill = billRepository.getAll(null, activeOnly = true)) {
                    is Result.Success -> {
                        updateState { it ->
                            val totalActiveBill = activeBill.data.sumOf { it.amount }
                            it.copy(
                                bill = if (totalActiveBill > 0) totalActiveBill.toDottedCurrency(
                                    prefix = ""
                                ) else "",
                                billIds = activeBill.data.map { it.id }
                            )
                        }
                    }

                    is Result.Error -> {
                        // TODO: Handle error
                    }
                }
            },
            viewModelScope.async {
                when (val announcements = announcementRepository.getAllAnnouncements()) {
                    is Result.Success -> {
                        updateState {
                            it.copy(
                                announcements = announcements.data
                                    .sortedByDescending { it.date }
                                    .take(3).map { announcement ->
                                        UiAnnouncement.fromDomain(announcement)
                                    }
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

    private suspend fun loadUsages(subscriberId: String) {
        when (val usage = usageRepository.getAll(subscriberId, 6)) {
            is Result.Success -> {
                val usage = usage.data
                val average = usage.map { it.amount }.average().takeIf { !it.isNaN() } ?: 0.0
                val max = usage.maxByOrNull { it.amount }

                updateState {
                    it.copy(
                        usage = usage.map {
                            WaterUsage(
                                usage = it.amount,
                                month = it.date.toMonthShort(),
                                isMaximum = it.id == max?.id
                            )
                        },
                        usageAverage = average,
                        usageMaximum = max?.amount ?: 0,
                        usageMaximumMonth = max?.date?.toMonthShort() ?: "-"
                    )
                }
            }

            is Result.Error -> {
                Log.e("DashboardViewModel", "Error: ${usage.error}")
                // TODO: Handle error
            }
        }
    }
}