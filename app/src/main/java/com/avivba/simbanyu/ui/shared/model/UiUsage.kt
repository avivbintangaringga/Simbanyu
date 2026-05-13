package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.ui.utils.toFullDateTime
import com.avivba.simbanyu.ui.utils.toMonthFull
import com.avivba.simbanyu.ui.utils.toYear
import java.time.Instant

data class UiUsage(
    val id: String,
    val meterStart: Int,
    val meterEnd: Int,
    val amount: Int,
    val periodMonth: String,
    val periodYear: Int,
    val imageUrl: String = "",
    val operatorNotes: String = "",
    val isExpanded: Boolean = false,
    val fullDate: String = "",
    val realDate: Instant
) {
    companion object {
        fun fromDomain(data: Usage) = UiUsage(
            id = data.id,
            meterStart = data.meterStart,
            meterEnd = data.meterEnd,
            amount = data.amount,
            periodMonth = data.date.toMonthFull(),
            periodYear = data.date.toYear().toInt(),
            imageUrl = data.evidenceImageUrl,
            operatorNotes = data.operatorNotes ?: "",
            fullDate = data.date.toFullDateTime(),
            realDate = data.date,
            isExpanded = false,
        )
    }
}