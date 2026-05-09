package com.avivba.simbanyu.data.remote.model.payment

import com.avivba.simbanyu.domain.model.Payment
import java.time.Instant

data class PaymentDto(
    val id: String,
    val orderId: String,
    val paymentType: String?,
    val paymentUrl: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain(): Payment {
        return Payment(
            id = id,
            orderId = orderId,
            paymentType = paymentType,
            paymentUrl = paymentUrl,
            status = Payment.PaymentStatus.valueOf(status),
            date = Instant.parse(createdAt),
        )
    }
}