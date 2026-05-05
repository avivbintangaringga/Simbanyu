package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.BillService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Bill
import com.avivba.simbanyu.domain.repository.BillRepository

class BillRepositoryImpl(
    private val billService: BillService
) : BillRepository {
    override suspend fun getAll(): Result<List<Bill>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Result<Bill, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getActive(): Result<List<Bill>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun requestPaymentLink(bills: List<Bill>): Result<String, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun payByCash(bill: Bill): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}