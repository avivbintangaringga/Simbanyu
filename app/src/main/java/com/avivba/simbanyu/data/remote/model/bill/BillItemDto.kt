package com.avivba.simbanyu.data.remote.model.bill

import androidx.annotation.Keep
import com.avivba.simbanyu.domain.model.Bill

@Keep
data class BillItemDto(
    val id: String,
    val name: String,
    val amount: Double,
) {
    fun toDomain(): Bill.BillItem =
        Bill.BillItem(
            id = id,
            name = name,
            amount = amount
        )
}