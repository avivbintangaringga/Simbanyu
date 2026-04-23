package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.screens.billhistory.BillingStatus

data class Bill(
    val id: String,
    val amount: String,
    val periodMonth: String,
    val periodYear: Int,
    val status: BillingStatus,
    val costs: List<Cost> = emptyList(),
    val isSelected: Boolean = false,
)
