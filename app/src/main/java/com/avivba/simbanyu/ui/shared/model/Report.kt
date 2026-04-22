package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.screens.report.ReportType

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
    val title: String,
    val type: ReportType,
    val user: User,
    val status: ReportStatus,
    val location: String,
    val locationCoordinate: Coordinate,
    val description: String,
    val image: String,
    val date: String,
    val operatorNotes: String = "",
    val operatorEvidenceImage: String = "",
    val isExpanded: Boolean = false,
)
