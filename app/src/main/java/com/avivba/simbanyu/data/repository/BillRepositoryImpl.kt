package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.di.BASE_URL
import com.avivba.simbanyu.data.remote.api.BillService
import com.avivba.simbanyu.data.remote.api.PaymentService
import com.avivba.simbanyu.data.remote.model.payment.CashPaymentRequest
import com.avivba.simbanyu.data.remote.model.payment.PaymentRequest
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Bill
import com.avivba.simbanyu.domain.model.Payment
import com.avivba.simbanyu.domain.repository.BillRepository

class BillRepositoryImpl(
    private val billService: BillService,
    private val paymentService: PaymentService
) : BillRepository {
    override suspend fun getAll(
        subscriberId: String?,
        limit: Int?,
        activeOnly: Boolean?
    ): Result<List<Bill>, DataError> {
        return safeApiCall {
            billService.getAll(
                subscriberId = subscriberId,
                limit = limit,
                activeOnly = activeOnly,
            ).data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun getById(id: String): Result<Bill, DataError> {
        return safeApiCall {
            billService.getById(id).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }

    override suspend fun requestPaymentLink(billIds: List<String>): Result<String, DataError> {
        return safeApiCall {
            paymentService.requestPayment(
                PaymentRequest(
                    billIds = billIds,
                    callbackUrl = "$BASE_URL/payment-success" // TODO: Change to real URL
                )
            ).data?.paymentUrl ?: throw Exception("Invalid response")
        }
    }

    override suspend fun payByCash(billIds: List<String>): Result<List<Payment>, DataError> {
        return safeApiCall {
            paymentService.requestCashPayment(
                CashPaymentRequest(
                    billIds = billIds
                )
            ).data?.map { it.toDomain() } ?: throw Exception("Invalid response")
        }
    }
}