package com.avivba.simbanyu.ui.screens.operator.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.UiReport
import com.avivba.simbanyu.ui.shared.model.UiReportStatus
import java.io.File

enum class ReportStatusFilter(
    val status: UiReportStatus?,
    val title: String
) {
    ALL(null, "Semua"),
    WAITING(UiReportStatus.WAITING, "Menunggu"),
    IN_PROGRESS(UiReportStatus.IN_PROGRESS, "Dalam Proses"),
    COMPLETED(UiReportStatus.COMPLETED, "Selesai"),
    REJECTED(UiReportStatus.REJECTED, "Ditolak"),
}

enum class Fields {
    STATUS,
    NOTES,
    IMAGE
}

data class OperatorReport(
    val report: UiReport,
    val isSending: Boolean = false,
)

data class OperatorReportUiState(
    val isLoading: Boolean = true,
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
    data class SelectCurrentReportStatus(val status: UiReportStatus) : OperatorReportAction
    data class ToggleItemExpand(val report: OperatorReport) : OperatorReportAction
    data object OpenCamera : OperatorReportAction
    data class SetImage(val image: File) : OperatorReportAction
}

sealed interface OperatorReportEvent {
    object OnSent : OperatorReportEvent
    object OpenCamera : OperatorReportEvent
}