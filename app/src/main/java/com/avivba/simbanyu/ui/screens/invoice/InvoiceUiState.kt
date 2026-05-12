package com.avivba.simbanyu.ui.screens.invoice

import com.avivba.simbanyu.ui.shared.model.UiBill
import com.avivba.simbanyu.ui.shared.model.UiBillingStatus
import com.avivba.simbanyu.ui.shared.model.UiCost


data class InvoiceUiState(
    val isLoading: Boolean = true,
    val isSending: Boolean = false,
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val category: String = "",
    val totalAmount: String = "",
    val bills: List<UiBill> = emptyList(),
)

sealed interface InvoiceAction {
    data object NavigateBack : InvoiceAction
    data object Pay : InvoiceAction
}

sealed interface InvoiceEvent {
    data object NavigateBack : InvoiceEvent
    data class OnPaymentSuccess(val url: String) : InvoiceEvent
    data class OnPaymentError(val message: String) : InvoiceEvent
}

val fakeInvoiceState = InvoiceUiState(
    totalAmount = "Rp 157.000",
    bills = listOf(
        UiBill(
            id = "INV-2023-10-001",
            periodMonth = "Oktober",
            periodYear = 2023,
            costs = listOf(
                UiCost("Biaya Pemakaian Air", "Rp 120.000"),
                UiCost("Biaya Administrasi", "Rp 10.000"),
                UiCost("Biaya Pemeliharaan Meter", "Rp 5.000")
            ),
            status = UiBillingStatus.UNPAID,
            amount = "Rp. 135.000",
            realAmount = 135000.0
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

