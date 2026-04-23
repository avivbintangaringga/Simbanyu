package com.avivba.simbanyu.ui.screens.operator.report

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.report.fakeReports
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OperatorReportViewModel(

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

                OperatorReportAction.PickImage -> {
                    // TODO: Pick Image
                }

                is OperatorReportAction.SetStatusFilter -> {
                    updateState {
                        it.copy(
                            filterStatus = action.filter
                        )
                    }
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                )
            }

            _reports.update {
                fakeReports.map { report ->
                    OperatorReport(
                        report = report,
                        false
                    )
                }
            }

            updateState {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun refresh() {

    }

    private fun send() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    currentReport = it.currentReport?.copy(
                        isSending = true
                    )
                )
            }

            delay(2000)

            updateState {
                it.copy(
                    currentReport = null
                )
            }
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

    private fun observeDataChanges() {
        viewModelScope.launch {
            combine(
                _reports,
                state.map { it.filterStatus }.distinctUntilChanged()
            ) { reports, filter ->
                if (filter.status == null) {
                    return@combine reports
                } else {
                    reports.filter { it.report.status == filter.status}
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
}