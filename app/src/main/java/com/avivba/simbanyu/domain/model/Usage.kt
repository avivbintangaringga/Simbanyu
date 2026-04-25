package com.avivba.simbanyu.domain.model

data class Usage(
    val id: String,
    val meterStart: Int,
    val amount: Int,
    val evidenceImageUrl: String,
    val operatorNotes: String,
)