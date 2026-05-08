package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.di.BASE_URL
import com.avivba.simbanyu.data.remote.api.BillService
import com.avivba.simbanyu.data.remote.api.PaymentService
import com.avivba.simbanyu.data.remote.model.payment.RequestPaymentRequest
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Bill
import com.avivba.simbanyu.domain.repository.BillRepository

class BillRepositoryImpl(
    private val billService: BillService,
    private val paymentService: PaymentService
) : BillRepository {
    override suspend fun getAll(): Result<List<Bill>, DataError> {
        return safeApiCall {
            billService.getAll().data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun getById(id: String): Result<Bill, DataError> {
        return safeApiCall {
            billService.getById(id).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }

    override suspend fun getActive(): Result<List<Bill>, DataError> {
        return safeApiCall {
            billService.getActive().data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun requestPaymentLink(bills: List<Bill>): Result<String, DataError> {
        return safeApiCall {
            paymentService.requestPayment(
                RequestPaymentRequest(
                    billIds = bills.map { it.id },
                    callbackUrl = "$BASE_URL/payment-success" // TODO: Change to real URL
                )
            ).data?.paymentUrl ?: throw Exception("Invalid response")
        }
    }

    override suspend fun payByCash(bill: Bill): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}