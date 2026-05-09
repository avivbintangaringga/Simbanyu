package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Payment(
    val id: String,
    val orderId: String,
    val paymentType: String?,
    val paymentUrl: String?,
    val status: PaymentStatus,
    val date: Instant,
) {
    enum class PaymentStatus {
        PENDING,
        SUCCESS,
        FAILED,
        REFUNDED
    }
}
