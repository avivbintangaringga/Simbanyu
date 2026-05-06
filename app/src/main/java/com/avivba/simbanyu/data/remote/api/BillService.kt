package com.avivba.simbanyu.data.remote.api

import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.bill.BillDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BillService {
    @GET("bills")
    suspend fun getAll(): Envelope<List<BillDto>>

    @GET("bills?activeOnly=true")
    suspend fun getActive(): Envelope<List<BillDto>>

    @GET("bills/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Envelope<BillDto>

    // TODO: Download receipt
}