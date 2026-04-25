package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Report(
    val id: String,
    val date: Instant,
    val address: String,
    val description: String,
    val evidenceImageUrl: String,
    val operatorNotes: String,
    val handlingEvidenceImageUrl: String,
    val status: ReportStatus,
    val subscriber: Subscriber
) {
    enum class ReportStatus {
        WAITING,
        IN_PROGRESS,
        COMPLETED,
        REJECTED
    }
}