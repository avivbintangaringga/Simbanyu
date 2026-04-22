package com.avivba.simbanyu.ui.screens.operator.report

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.screens.report.fakeReports
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OperatorReportViewModel(

) : BaseViewModel<OperatorReportUiState, OperatorReportAction, OperatorReportEvent>(
    OperatorReportUiState()
) {
    init {
        viewModelScope.launch {
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

            updateState {
                it.copy(
                    reports = fakeReports.map { report ->
                        OperatorReport(
                            report = report,
                            false
                        )
                    }
                )
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
}