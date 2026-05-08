package com.avivba.simbanyu.data.remote.model.report

import androidx.annotation.Keep

@Keep
data class ReportUpdateSubmissionDto(
    val status: ReportStatus,
    val operatorNotes: String? = null,
)
