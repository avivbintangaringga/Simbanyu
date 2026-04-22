package com.avivba.simbanyu.ui.screens.operator.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Report

data class OperatorReport(
    val report: Report,
    val isSending: Boolean = false,
)

data class OperatorReportUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val reports: List<OperatorReport> = emptyList(),
    val currentReport: OperatorReport? = null,
    val notesState: TextFieldState = TextFieldState(),
)

sealed interface OperatorReportAction {
    data object Load : OperatorReportAction
    data object Refresh : OperatorReportAction
    data object HideUpdateReport : OperatorReportAction
    data class UpdateReport(val report: OperatorReport) : OperatorReportAction
    data class ToggleItemExpand(val report: OperatorReport) : OperatorReportAction
    data object SendUpdatedReport: OperatorReportAction
}

sealed interface OperatorReportEvent {
    object OnSent: OperatorReportEvent
}