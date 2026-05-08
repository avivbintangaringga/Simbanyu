package com.avivba.simbanyu.data.remote.model.payment

import androidx.annotation.Keep

@Keep
data class RequestPaymentResponse(
    val paymentUrl: String
)
