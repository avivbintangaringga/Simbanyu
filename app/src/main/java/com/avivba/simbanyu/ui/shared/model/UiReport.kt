package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toFullDateTime

data class UiReport(
    val id: String,
    val subscriber: UiSubscriber,
    val status: UiReportStatus,
    val address: String,
    val coordinate: UiCoordinate?,
    val description: String,
    val evidenceImageUrl: String,
    val date: String,
    val operatorNotes: String = "",
    val handlingEvidenceImageUrl: String = "",
    val isExpanded: Boolean = false,
) {
    companion object {
        fun fromDomain(report: com.avivba.simbanyu.domain.model.Report): UiReport {
            return UiReport(
                id = report.id,
                subscriber = UiSubscriber.fromDomain(report.subscriber),
                status = UiReportStatus.valueOf(report.status.name),
                address = report.address,
                coordinate = report.coordinate?.let { UiCoordinate.fromDomain(it) },
                description = report.description,
                evidenceImageUrl = report.evidenceImageUrl,
                date = report.date.toFullDateTime(),
                operatorNotes = report.operatorNotes,
                handlingEvidenceImageUrl = report.handlingEvidenceImageUrl,
            )
        }
    }
}
