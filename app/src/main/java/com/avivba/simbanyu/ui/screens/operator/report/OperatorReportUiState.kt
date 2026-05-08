package com.avivba.simbanyu.ui.screens.operator.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Report
import com.avivba.simbanyu.ui.shared.model.ReportStatus

enum class ReportStatusFilter(
    val status: ReportStatus?,
    val title: String
) {
    ALL(null, "Semua"),
    WAITING(ReportStatus.WAITING, "Menunggu"),
    IN_PROGRESS(ReportStatus.IN_PROGRESS, "Dalam Proses"),
    COMPLETED(ReportStatus.COMPLETED, "Selesai"),
    REJECTED(ReportStatus.REJECTED, "Ditolak"),
}


data class OperatorReport(
    val report: Report,
    val isSending: Boolean = false,
)

data class OperatorReportUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val reports: List<OperatorReport> = emptyList(),
    val filterStatus: ReportStatusFilter = ReportStatusFilter.ALL,
    val currentReport: OperatorReport? = null,
    val notesState: TextFieldState = TextFieldState(),
    val imageUri: String = "",
)

sealed interface OperatorReportAction {
    data class CapturedImage(val file: java.io.File) : OperatorReportAction
    data object Load : OperatorReportAction
    data object Refresh : OperatorReportAction
    data object HideUpdateReport : OperatorReportAction
    data class SetStatusFilter(val filter: ReportStatusFilter) : OperatorReportAction
    data class UpdateReport(val report: OperatorReport) : OperatorReportAction
    data class ToggleItemExpand(val report: OperatorReport) : OperatorReportAction
    data object SendUpdatedReport : OperatorReportAction
    data object PickImage : OperatorReportAction
}

sealed interface OperatorReportEvent {
    object OnSent : OperatorReportEvent
}