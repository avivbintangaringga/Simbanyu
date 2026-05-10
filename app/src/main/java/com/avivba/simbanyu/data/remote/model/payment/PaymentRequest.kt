package com.avivba.simbanyu.data.remote.model.payment

import androidx.annotation.Keep

@Keep
data class PaymentRequest(
    val billIds: List<String>,
    val callbackUrl: String,
)
