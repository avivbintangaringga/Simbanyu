package com.avivba.simbanyu.data.remote.api

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.Envelope
import com.avivba.simbanyu.data.remote.model.bill.BillDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Keep
interface BillService {
    @GET("bills")
    suspend fun getAll(
        @Query("subscriberId") subscriberId: String?,
        @Query("limit") limit: Int? = null,
        @Query("activeOnly") activeOnly: Boolean? = null
    ): Envelope<List<BillDto>>


    @GET("bills/{id}")
    suspend fun getById(
        @Path("id") id: String
    ): Envelope<BillDto>

    // TODO: Download receipt
}