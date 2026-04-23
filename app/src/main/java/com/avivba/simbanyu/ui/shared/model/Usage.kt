package com.avivba.simbanyu.ui.shared.model

data class Usage(
    val id: String,
    val meterStart: Int,
    val meterEnd: Int,
    val amount: Int,
    val periodMonth: String,
    val periodYear: Int,
)

