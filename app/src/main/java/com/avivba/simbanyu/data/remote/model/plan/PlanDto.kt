package com.avivba.simbanyu.data.remote.model.plan

data class PlanDto(
    val id: String,
    val name: String,
    val basePrice: Double,
    val prices: List<com.avivba.simbanyu.data.remote.model.price.PriceDto>
) {
    companion object {
    }
}
