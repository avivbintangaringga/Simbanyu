package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.payment.RequestPaymentRequest
import com.avivba.simbanyu.data.remote.model.payment.RequestPaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

@Keep
interface PaymentService {
    @POST("payments")
    suspend fun requestPayment(
        @Body paymentRequest: RequestPaymentRequest
    ): Envelope<RequestPaymentResponse>
}