package com.avivba.simbanyu.ui.screens.operator.report

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.ReportUpdateSubmission
import com.avivba.simbanyu.domain.repository.ReportRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Report
import com.avivba.simbanyu.ui.shared.model.ReportStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OperatorReportViewModel(
    private val reportRepository: ReportRepository
) : BaseViewModel<OperatorReportUiState, OperatorReportAction, OperatorReportEvent>(
    OperatorReportUiState()
) {
    private val _reports = MutableStateFlow<List<OperatorReport>>(emptyList())

    init {
        viewModelScope.launch {
            load()
            observeDataChanges()
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
                            currentReport = null,
                            errorFields = emptyList(),
                            notesState = TextFieldState(),
                            image = null
                        )
                    }
                }

                is OperatorReportAction.ShowUpdateReport -> {
                    updateState {
                        it.copy(
                            currentReport = action.report,
                            notesState = TextFieldState(action.report.report.operatorNotes)
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

                is OperatorReportAction.SelectCurrentReportStatus -> {
                    updateState {
                        it.copy(
                            currentReport = it.currentReport?.copy(
                                report = it.currentReport.report.copy(
                                    status = action.status
                                )
                            )
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
                _reports.update {
                    reports.data
                        .sortedByDescending {
                            it.date
                        }
                        .map { report ->
                            Log.d("SDKSDK", "REPORT: $report")
                            OperatorReport(
                                report = Report.fromDomain(report)
                            )
                        }
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }

    private fun validateFields(): Boolean {
        val report = state.value.currentReport?.report ?: return false

        return !(report.status == ReportStatus.COMPLETED &&
                state.value.image == null &&
                report.handlingEvidenceImageUrl.isEmpty())
    }

    private suspend fun send() {
        val report = state.value.currentReport ?: return
        if (!report.isSending) {
            updateState {
                it.copy(
                    errorFields = emptyList(),
                    currentReport = it.currentReport?.copy(
                        isSending = true
                    )
                )
            }

            if (!validateFields()) {
                updateState {
                    it.copy(
                        currentReport = it.currentReport?.copy(
                            isSending = false
                        )
                    )
                }
                return
            }

            val submission = ReportUpdateSubmission(
                status = com.avivba.simbanyu.domain.model.Report.ReportStatus.valueOf(
                    report.report.status.id
                ),
                handlingEvidenceImage = state.value.image,
                operatorNotes = state.value.notesState.text.toString()
            )

            when (reportRepository.handle(report.report.id, submission)) {
                is Result.Success -> {
                    updateState {
                        it.copy(
                            currentReport = null,
                        )
                    }
                    sendEvent(OperatorReportEvent.OnSent)
                }

                is Result.Error -> {
                    updateState {
                        it.copy(
                            currentReport = it.currentReport?.copy(
                                isSending = false
                            )
                        )
                    }
                    // TODO: Handle error
                }
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
                        item.copy(
                            report = r.copy(
                                isExpanded = false
                            )
                        )
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