package com.avivba.simbanyu.data.remote.model.bill

import androidx.annotation.Keep
import com.avivba.simbanyu.domain.model.Bill
import java.time.Instant

@Keep
data class BillDto(
    val id: String,
    val title: String,
    val amount: Double,
    val status: String,
    val items: List<BillItemDto>,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain(): Bill =
        Bill(
            id = id,
            amount = amount,
            date = Instant.parse(createdAt),
            status = Bill.BillStatus.valueOf(status.uppercase()),
            items = items.map { it.toDomain() }
        )
}