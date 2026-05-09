package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Bill

interface BillRepository {
    suspend fun getAll(subscriberId: String?, limit: Int? = null, activeOnly: Boolean? = false): Result<List<Bill>, DataError>
    suspend fun getById(id: String): Result<Bill, DataError>
    suspend fun requestPaymentLink(bills: List<Bill>): Result<String, DataError>
    suspend fun payByCash(bill: Bill): Result<Unit, DataError>
}