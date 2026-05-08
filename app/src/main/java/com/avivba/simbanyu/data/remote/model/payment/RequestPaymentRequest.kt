package com.avivba.simbanyu.data.remote.model.payment

import androidx.annotation.Keep

@Keep
data class RequestPaymentRequest(
    val billIds: List<String>,
    val callbackUrl: String,
)
