package com.avivba.simbanyu.domain.model

import java.io.File
import java.time.Instant

data class ReportSubmission(
    val address: String,
    val description: String,
    val evidenceImage: File,
)