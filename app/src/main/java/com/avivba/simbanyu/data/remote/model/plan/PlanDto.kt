package com.avivba.simbanyu.data.remote.model.plan

import com.avivba.simbanyu.data.remote.model.price.PriceDto

data class PlanDto(
    val id: String,
    val name: String,
    val basePrice: Double,
    val prices: List<PriceDto>
)
