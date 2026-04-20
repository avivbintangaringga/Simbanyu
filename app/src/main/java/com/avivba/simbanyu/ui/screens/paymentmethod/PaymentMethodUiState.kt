package com.avivba.simbanyu.ui.screens.paymentmethod

data class PaymentMethod(
    val id: String,
    val name: String,
    val fee: String,
    val isAvailable: Boolean,
    val imageUrl: String
)

data class PaymentMethodUiState(
    val isLoading: Boolean = false,
    val selectedPaymentMethod: PaymentMethod? = null,
    val eWallet: List<PaymentMethod> = emptyList(),
    val bankTransfer: List<PaymentMethod> = emptyList(),
    val retail: List<PaymentMethod> = emptyList(),
)

sealed interface PaymentMethodUiAction {
    data class SelectPaymentMethod(val paymentMethod: PaymentMethod) : PaymentMethodUiAction
    data object Choose : PaymentMethodUiAction
    data object GoBack : PaymentMethodUiAction
}

sealed interface PaymentMethodUiEvent {
    data object GoBack : PaymentMethodUiEvent
}

val fakeEWallet = listOf(
    PaymentMethod(
        id = "1",
        name = "GoPay",
        fee = "Gratis",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/86/Gopay_logo.svg"
    ),
    PaymentMethod(
        id = "2",
        name = "OVO",
        fee = "Rp 1.000",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/eb/Logo_ovo_purple.svg"
    ),
    PaymentMethod(
        id = "3",
        name = "Dana",
        fee = "Gratis",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/72/Logo_dana_blue.svg"
    )
)

val fakeBankTransfer = listOf(
    PaymentMethod(
        id = "4",
        name = "Bank BCA",
        fee = "Rp 2.500",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/id/e/e0/BCA_logo.svg"
    ),
    PaymentMethod(
        id = "5",
        name = "Bank Mandiri",
        fee = "Rp 2.500",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ad/Bank_Mandiri_logo_2016.svg"
    ),
    PaymentMethod(
        id = "6",
        name = "Bank BRI",
        fee = "Rp 2.500",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2e/BRI_2020.svg"
    )
)

val fakeRetail = listOf(
    PaymentMethod(
        id = "7",
        name = "Alfamart",
        fee = "Rp 5.000",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/86/Alfamart_logo.svg"
    ),
    PaymentMethod(
        id = "8",
        name = "Indomaret",
        fee = "Rp 5.000",
        isAvailable = true,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/Logo_Indomaret.svg"
    ),
    PaymentMethod(
        id = "9",
        name = "Lawson",
        fee = "Rp 5.000",
        isAvailable = false,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a2/Lawson_logo.svg"
    )
)


