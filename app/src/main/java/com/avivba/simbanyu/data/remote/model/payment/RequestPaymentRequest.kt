package com.avivba.simbanyu.data.remote.model.payment

data class RequestPaymentRequest(
    val billIds: List<String>,
    val callbackUrl: String,
)
