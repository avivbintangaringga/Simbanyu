package com.avivba.simbanyu.ui.screens.operator.report

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.repository.ReportRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Report
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OperatorReportViewModel(
   private val reportRepository: ReportRepository
) : BaseViewModel<OperatorReportUiState, OperatorReportAction, OperatorReportEvent>(
    OperatorReportUiState()
) {
    private val _reports = MutableStateFlow<List<OperatorReport>>(emptyList())

    init {
        viewModelScope.launch {
            observeDataChanges()
            load()
        }
    }

    override fun onAction(action: OperatorReportAction) {
        viewModelScope.launch {
            when (action) {
                OperatorReportAction.Load -> load()
                OperatorReportAction.Refresh -> refresh()
                is OperatorReportAction.SendUpdatedReport -> send()
                is OperatorReportAction.ToggleItemExpand -> toggleReportExpand(action.report)
                OperatorReportAction.HideUpdateReport -> {
                    updateState {
                        it.copy(
                            currentReport = null
                        )
                    }
                }

                is OperatorReportAction.UpdateReport -> {
                    updateState {
                        it.copy(
                            currentReport = action.report
                        )
                    }
                }

                OperatorReportAction.OpenCamera -> {
                    sendEvent(OperatorReportEvent.OpenCamera)
                }

                is OperatorReportAction.SetStatusFilter -> {
                    updateState {
                        it.copy(
                            filterStatus = action.filter
                        )
                    }
                }

                is OperatorReportAction.SetImage -> {
                    updateState {
                        it.copy(
                            image = action.image
                        )
                    }
                }
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
                isRefreshing = false
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
        when (val reports = reportRepository.getAll()) {
            is Result.Success -> {
                updateState {
                    it.copy(
                        reports = reports.data.map { report ->
                            OperatorReport(
                                report = Report.fromDomain(report)
                            )
                        }
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }

    private suspend fun send() {
        updateState {
            it.copy(
                currentReport = it.currentReport?.copy(
                    isSending = true
                )
            )
        }

        // TODO: Send

        updateState {
            it.copy(
                currentReport = null
            )
        }
    }

    private fun toggleReportExpand(report: OperatorReport) {
        updateState {
            it.copy(
                reports = it.reports.map { item ->
                    val r = item.report
                    if (r.id == report.report.id) {
                        item.copy(
                            report = r.copy(
                                isExpanded = !r.isExpanded
                            )
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
            _reports,
            state.map { it.filterStatus }.distinctUntilChanged()
        ) { reports, filter ->
            if (filter.status == null) {
                return@combine reports
            } else {
                reports.filter { it.report.status == filter.status }
            }
        }.collect { filtered ->
            updateState {
                it.copy(
                    reports = filtered
                )
            }
        }
    }
}