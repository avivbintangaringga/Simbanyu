package com.avivba.simbanyu.ui.shared.model

enum class UiReportStatus(
    val id: String,
    val status: String,
) {
    WAITING(
        id = com.avivba.simbanyu.domain.model.Report.ReportStatus.WAITING.name,
        status = "Menunggu"
    ),
    IN_PROGRESS(
        id = com.avivba.simbanyu.domain.model.Report.ReportStatus.IN_PROGRESS.name,
        status = "Dalam Proses"
    ),
    COMPLETED(
        id = com.avivba.simbanyu.domain.model.Report.ReportStatus.COMPLETED.name,
        status = "Selesai"
    ),
    REJECTED(
        id = com.avivba.simbanyu.domain.model.Report.ReportStatus.REJECTED.name,
        status = "Ditolak"
    ),
}

