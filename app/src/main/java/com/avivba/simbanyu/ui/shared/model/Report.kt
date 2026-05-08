package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toFullDateTime

enum class ReportStatus(
    val id: String,
    val status: String,
) {
    WAITING("waiting", "Menunggu"),
    IN_PROGRESS("in_progress", "Dalam Proses"),
    COMPLETED("completed", "Selesai"),
    REJECTED("rejected", "Ditolak"),
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
