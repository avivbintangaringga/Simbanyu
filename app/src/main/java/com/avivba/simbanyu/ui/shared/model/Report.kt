package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toFullDateTime

enum class ReportStatus(
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

data class Report(
    val id: String,
    val subscriber: Subscriber,
    val status: ReportStatus,
    val address: String,
    val coordinate: Coordinate?,
    val description: String,
    val evidenceImageUrl: String,
    val date: String,
    val operatorNotes: String = "",
    val handlingEvidenceImageUrl: String = "",
    val isExpanded: Boolean = false,
) {
    companion object {
        fun fromDomain(report: com.avivba.simbanyu.domain.model.Report): Report {
            return Report(
                id = report.id,
                subscriber = Subscriber.fromDomain(report.subscriber),
                status = ReportStatus.valueOf(report.status.name),
                address = report.address,
                coordinate = report.coordinate?.let { Coordinate.fromDomain(it) },
                description = report.description,
                evidenceImageUrl = report.evidenceImageUrl,
                date = report.date.toFullDateTime(),
                operatorNotes = report.operatorNotes,
                handlingEvidenceImageUrl = report.handlingEvidenceImageUrl,
            )
        }
    }
}
