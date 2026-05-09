package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Bill(
    val id: String,
    val amount: Double,
    val date: Instant,
    val status: BillStatus,
    val items: List<BillItem>,
    val payments: List<Payment>
) {
    enum class BillStatus {
        UNPAID,
        PAID,
        PENDING
    }

    data class BillItem(
        val id: String,
        val name: String,
        val amount: Double
    )
}