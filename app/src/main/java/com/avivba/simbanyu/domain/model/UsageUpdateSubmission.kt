package com.avivba.simbanyu.domain.model

import java.io.File

data class UsageUpdateSubmission(
    val currentMeter: Int,
    val evidenceImage: File?,
    val operatorNotes: String?,
)