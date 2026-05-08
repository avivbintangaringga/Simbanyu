package com.avivba.simbanyu.data.remote.model.report

data class ReportUpdateSubmissionDto(
    val status: com.avivba.simbanyu.data.remote.model.report.ReportStatus,
    val operatorNotes: String? = null,
)
