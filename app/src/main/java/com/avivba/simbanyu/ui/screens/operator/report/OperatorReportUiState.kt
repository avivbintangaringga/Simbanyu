package com.avivba.simbanyu.ui.screens.operator.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Report
import com.avivba.simbanyu.ui.shared.model.ReportStatus
import java.io.File

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

enum class Fields {
    STATUS,
    NOTES,
    IMAGE
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
    val errorFields: List<Fields> = emptyList(),
    val currentReport: OperatorReport? = null,
    val notesState: TextFieldState = TextFieldState(),
    val image: File? = null,
)

sealed interface OperatorReportAction {
    data object Load : OperatorReportAction
    data object Refresh : OperatorReportAction
    data class SetStatusFilter(val filter: ReportStatusFilter) : OperatorReportAction
    data class ShowUpdateReport(val report: OperatorReport) : OperatorReportAction
    data object HideUpdateReport : OperatorReportAction
    data object SendUpdatedReport : OperatorReportAction
    data class SelectCurrentReportStatus(val status: ReportStatus) : OperatorReportAction
    data class ToggleItemExpand(val report: OperatorReport) : OperatorReportAction
    data object OpenCamera : OperatorReportAction
    data class SetImage(val image: File) : OperatorReportAction
}

sealed interface OperatorReportEvent {
    object OnSent : OperatorReportEvent
    object OpenCamera : OperatorReportEvent
}