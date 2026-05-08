package com.avivba.simbanyu.domain.model

import java.io.File

data class ReportUpdateSubmission(
    val id: String,
    val status: Report.ReportStatus,
    val handlingEvidenceImage: File,
    val operatorNotes: String
)