package com.avivba.simbanyu.ui.screens.billdetails

import com.avivba.simbanyu.ui.shared.model.BillingStatus
import com.avivba.simbanyu.ui.shared.model.Cost

data class BillDetailsUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val category: String = "",
    val period: String = "",
    val details: List<Cost> = emptyList(),
    val status: BillingStatus = BillingStatus.UNPAID,
    val totalAmount: String = "",
    val paymentMethodName: String = "",
    val paymentMethodImageUrl: String = "",
    val transactionDate: String = "",
    val referenceNumber: String = "",
)

sealed interface BillDetailsAction {
    data object NavigateBack : BillDetailsAction
    data object DownloadReceipt : BillDetailsAction
    data object OpenPaymentPage : BillDetailsAction
}

sealed interface BillDetailsEvent {
    data object NavigateBack : BillDetailsEvent
    data class OpenPaymentPage(val url: String) : BillDetailsEvent
}

val fakeBillDetailsUiState = BillDetailsUiState(
    name = "Budi Setiawan",
    address = "Jl. Merdeka No. 123, Jakarta",
    meterId = "123456789012",
    category = "Rumah Tangga A1",
    period = "Oktober 2023",
    details = listOf(
        Cost("Tagihan Air", "Rp 150.000"),
        Cost("Biaya Admin", "Rp 2.500"),
        Cost("Pemeliharaan Metro", "Rp 5.000")
    ),
    totalAmount = "Rp 157.500",
    paymentMethodName = "GoPay",
    paymentMethodImageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/86/Gopay_logo.svg",
    transactionDate = "25 Okt 2023, 14:30",
    referenceNumber = "SB-9876543210",
    status = BillingStatus.PENDING,
)

