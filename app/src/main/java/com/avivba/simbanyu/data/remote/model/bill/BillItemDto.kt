package com.avivba.simbanyu.data.remote.model.bill

import com.avivba.simbanyu.domain.model.Bill

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