package com.avivba.simbanyu.data.remote.model.report

data class HandlingRequest(
    val status: com.avivba.simbanyu.data.remote.model.report.ReportStatus,
    val operatorNotes: String? = null,
)
