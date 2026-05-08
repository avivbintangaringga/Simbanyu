package com.avivba.simbanyu.data.remote.model.report

import androidx.annotation.Keep

@Keep
data class ReportSubmissionDto(
    val address: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)