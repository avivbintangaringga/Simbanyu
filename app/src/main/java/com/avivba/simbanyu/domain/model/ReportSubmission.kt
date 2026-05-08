package com.avivba.simbanyu.domain.model

import java.io.File

data class ReportSubmission(
    val address: String,
    val coordinate: Coordinate?,
    val description: String,
    val evidenceImage: File,
)