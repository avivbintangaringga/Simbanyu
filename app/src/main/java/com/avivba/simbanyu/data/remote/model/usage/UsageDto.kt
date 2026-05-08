package com.avivba.simbanyu.data.remote.model.usage

import com.avivba.simbanyu.data.di.BASE_URL
import com.avivba.simbanyu.domain.model.Usage
import java.time.Instant

data class UsageDto(
    val id: String,
    val meterStart: Int,
    val meterEnd: Int,
    val amount: Int,
    val evidenceImageUrl: String,
    val operatorNotes: String? = null,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain() = Usage(
        id = id,
        meterStart = meterStart,
        meterEnd = meterEnd,
        amount = amount,
        evidenceImageUrl = "$BASE_URL$evidenceImageUrl",
        operatorNotes = operatorNotes ?: "",
        date = Instant.parse(createdAt),
    )
}