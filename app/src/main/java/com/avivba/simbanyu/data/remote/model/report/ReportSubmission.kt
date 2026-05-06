package com.avivba.simbanyu.data.remote.model.report

data class ReportSubmission(
    val address: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)