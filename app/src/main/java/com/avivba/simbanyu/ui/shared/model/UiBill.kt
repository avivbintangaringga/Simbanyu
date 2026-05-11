package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.domain.model.Bill
import com.avivba.simbanyu.ui.screens.billdetails.UiPayment
import com.avivba.simbanyu.ui.utils.toDottedCurrency
import com.avivba.simbanyu.ui.utils.toMonthFull
import com.avivba.simbanyu.ui.utils.toYear

data class UiBill(
    val id: String,
    val amount: String,
    val realAmount: Double,
    val periodMonth: String,
    val periodYear: Int,
    val status: UiBillingStatus,
    val costs: List<UiCost> = emptyList(),
    val payments: List<UiPayment> = emptyList(),
    val isSelected: Boolean = false,
) {
    companion object {
        fun fromDomain(bill: Bill): UiBill {
            return UiBill(
                id = bill.id,
                realAmount = bill.amount,
                amount = bill.amount.toDottedCurrency(),
                periodMonth = bill.date.toMonthFull(),
                periodYear = bill.date.toYear().toInt(),
                status = when (bill.status) {
                    Bill.BillStatus.PAID -> UiBillingStatus.PAID
                    Bill.BillStatus.PENDING -> UiBillingStatus.PENDING
                    Bill.BillStatus.UNPAID -> UiBillingStatus.UNPAID
                },
                costs = bill.items.map { UiCost(it.name, it.amount.toDottedCurrency()) },
                payments = bill.payments
                    .sortedByDescending { it.date }
                    .map { UiPayment.fromDomain(it) },
                isSelected = false
            )
        }
    }
}