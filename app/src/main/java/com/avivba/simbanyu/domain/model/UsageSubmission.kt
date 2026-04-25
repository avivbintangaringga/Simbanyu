package com.avivba.simbanyu.domain.model

import java.io.File

data class UsageSubmission(
    val id: String,
    val currentMeter: Int,
    val evidenceImage: File,
    val operatorNotes: String,
)