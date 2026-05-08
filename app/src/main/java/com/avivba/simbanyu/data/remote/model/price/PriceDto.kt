package com.avivba.simbanyu.data.remote.model.price

import androidx.annotation.Keep

@Keep
data class PriceDto(
    val id: String,
    val name: String,
    val tierOrder: Int,
    val maxVolume: Int?,
    val pricePerMeter: Double
)