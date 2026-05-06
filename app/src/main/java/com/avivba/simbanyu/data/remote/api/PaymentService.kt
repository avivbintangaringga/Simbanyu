package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.payment.RequestPaymentRequest
import com.avivba.simbanyu.data.remote.model.payment.RequestPaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentService {
    @POST("payments")
    suspend fun requestPayment(
        @Body paymentRequest: RequestPaymentRequest
    ): Envelope<RequestPaymentResponse>
}