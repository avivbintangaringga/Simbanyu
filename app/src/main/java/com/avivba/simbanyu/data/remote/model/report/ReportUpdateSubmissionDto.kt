package com.avivba.simbanyu.data.remote.model.report

data class ReportUpdateSubmissionDto(
    val status: ReportStatus,
    val operatorNotes: String? = null,
)
