package com.avivba.simbanyu.domain.model

import java.io.File

data class UsageSubmission(
    val subscriberId: String,
    val currentMeter: Int,
    val evidenceImage: File,
    val operatorNotes: String,
)