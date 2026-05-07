package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toDottedString
import com.avivba.simbanyu.ui.utils.toMonthFull
import com.avivba.simbanyu.ui.utils.toYear

data class Bill(
    val id: String,
    val amount: String,
    val periodMonth: String,
    val periodYear: Int,
    val status: BillingStatus,
    val costs: List<Cost> = emptyList(),
    val isSelected: Boolean = false,
) {
    companion object {
        fun fromDomain(bill: com.avivba.simbanyu.domain.model.Bill): Bill {
            return Bill(
                id = bill.id,
                amount = bill.amount.toDottedString(),
                periodMonth = bill.date.toMonthFull(),
                periodYear = bill.date.toYear().toInt(),
                status = when (bill.status) {
                    com.avivba.simbanyu.domain.model.Bill.BillStatus.PAID -> BillingStatus.PAID
                    com.avivba.simbanyu.domain.model.Bill.BillStatus.PENDING -> BillingStatus.PENDING
                    com.avivba.simbanyu.domain.model.Bill.BillStatus.UNPAID -> BillingStatus.UNPAID
                },
                costs = bill.items.map { Cost(it.name, it.amount.toDottedString()) },
                isSelected = false
            )
        }
    }
}