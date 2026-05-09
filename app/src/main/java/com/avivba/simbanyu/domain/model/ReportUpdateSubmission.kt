package com.avivba.simbanyu.domain.model

import java.io.File

data class ReportUpdateSubmission(
    val status: Report.ReportStatus,
    val handlingEvidenceImage: File?,
    val operatorNotes: String?
)