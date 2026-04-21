package com.avivba.simbanyu.ui.screens.invoice

import com.avivba.simbanyu.ui.screens.billhistory.BillingStatus


data class Cost(
    val name: String,
    val amount: String,
)

data class Bill(
    val id: String,
    val periodMonth: String,
    val periodYear: Int,
    val details: List<Cost>,
    val subtotal: String
)

data class InvoiceUiState(
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val category: String = "",

    val totalAmount: String = "",
    val bills: List<Bill> = emptyList(),
//    val paymentMethodId: String = "",
//    val paymentMethodImage: String = "",
//    val paymentMethodName: String = "",
//    val paymentMethodFee: String = "",
)

sealed interface InvoiceAction {
    data object NavigateBack : InvoiceAction
    data object Pay : InvoiceAction
    data object SelectPaymentMethod : InvoiceAction
}

sealed interface InvoiceEvent {
    data object NavigateBack : InvoiceEvent
    data object GoToPaymentMethod : InvoiceEvent
}

val fakeInvoiceState = InvoiceUiState(
    totalAmount = "Rp 157.000",
    bills = listOf(
        Bill(
            id = "INV-2023-10-001",
            periodMonth = "Oktober",
            periodYear = 2023,
            details = listOf(
                Cost("Biaya Pemakaian Air", "Rp 120.000"),
                Cost("Biaya Administrasi", "Rp 10.000"),
                Cost("Biaya Pemeliharaan Meter", "Rp 5.000")
            ),
            subtotal = "Rp 135.000"
        ),
//        Bill(
//            id = "INV-2023-11-001",
//            periodMonth = "November",
//            periodYear = 2023,
//            details = listOf(
//                Cost("Biaya Air (Sisa)", "Rp 21.000"),
//                Cost("Pajak", "Rp 1.000")
//            ),
//            subtotal = "Rp 22.000"
//        )
    ),
//    paymentMethodId = "pm_gopay",
//    paymentMethodName = "Gopay",
//    paymentMethodFee = "Rp 2.500",
//    paymentMethodImage = "",
    name = "Raditya Dika",
    address = "Sawahan 5, Jatiayu, Karangmojo",
    meterId = "123456789",
    category = "Rumah Tangga"
)

