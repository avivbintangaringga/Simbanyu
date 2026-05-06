package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Usage(
    val id: String,
    val meterStart: Int,
    val meterEnd: Int,
    val amount: Int,
    val evidenceImageUrl: String,
    val operatorNotes: String?,
    val date: Instant,
)