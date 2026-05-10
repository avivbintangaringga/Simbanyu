package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.payment.CashPaymentRequest
import com.avivba.simbanyu.data.remote.model.payment.PaymentDto
import com.avivba.simbanyu.data.remote.model.payment.PaymentRequest
import com.avivba.simbanyu.data.remote.model.payment.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

@Keep
interface PaymentService {
    @POST("payments")
    suspend fun requestPayment(
        @Body paymentRequest: PaymentRequest
    ): Envelope<PaymentResponse>

    @POST("payments/cash")
    suspend fun requestCashPayment(
        @Body request: CashPaymentRequest
    ): Envelope<List<PaymentDto>>
}