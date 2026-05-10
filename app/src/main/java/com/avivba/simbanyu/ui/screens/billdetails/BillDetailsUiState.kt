package com.avivba.simbanyu.ui.screens.billdetails

import com.avivba.simbanyu.domain.model.Payment
import com.avivba.simbanyu.ui.shared.model.UiBillingStatus
import com.avivba.simbanyu.ui.shared.model.UiCost
import com.avivba.simbanyu.ui.utils.toFullDateTime

enum class UiPaymentStatus(val status: String) {
    PENDING("Menunggu Pembayaran"),
    SUCCESS("Berhasil"),
    FAILED("Gagal"),
    REFUNDED("Dikembalikan");
}

data class UiPayment(
    val id: String,
    val orderId: String,
    val paymentType: String?,
    val paymentUrl: String?,
    val status: UiPaymentStatus,
    val date: String,
) {
    companion object {
        fun fromDomain(payment: Payment): UiPayment {
            return UiPayment(
                id = payment.id,
                orderId = payment.orderId,
                paymentType = payment.paymentType,
                paymentUrl = payment.paymentUrl,
                status = when (payment.status) {
                    Payment.PaymentStatus.PENDING -> UiPaymentStatus.PENDING
                    Payment.PaymentStatus.SUCCESS -> UiPaymentStatus.SUCCESS
                    Payment.PaymentStatus.FAILED -> UiPaymentStatus.FAILED
                    Payment.PaymentStatus.REFUNDED -> UiPaymentStatus.REFUNDED
                },
                date = payment.date.toFullDateTime(),
            )
        }
    }
}

data class BillDetailsUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val address: String = "",
    val meterId: String = "",
    val category: String = "",
    val period: String = "",
    val details: List<UiCost> = emptyList(),
    val status: UiBillingStatus = UiBillingStatus.UNPAID,
    val totalAmount: String = "",
    val payments: List<UiPayment> = emptyList(),
)

sealed interface BillDetailsAction {
    data object Load : BillDetailsAction
    data object NavigateBack : BillDetailsAction
    data object DownloadReceipt : BillDetailsAction
    data class OpenPaymentPage(val url: String) : BillDetailsAction
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
        UiCost("Tagihan Air", "Rp 150.000"),
        UiCost("Biaya Admin", "Rp 2.500"),
        UiCost("Pemeliharaan Metro", "Rp 5.000")
    ),
    totalAmount = "Rp 157.500",
    status = UiBillingStatus.PENDING,
    payments = listOf(
        UiPayment(
            id = "1",
            orderId = "1234567890",
            paymentType = "Bank BCA",
            paymentUrl = "https://example.com/payment/1234567890",
            status = UiPaymentStatus.SUCCESS,
            date = "2023-10-01 10:00:00"
        )
    )
)

