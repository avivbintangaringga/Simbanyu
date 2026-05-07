package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.ui.utils.toMonthFull
import com.avivba.simbanyu.ui.utils.toYear

data class Usage(
    val id: String,
    val meterStart: Int,
    val meterEnd: Int,
    val amount: Int,
    val periodMonth: String,
    val periodYear: Int,
    val imageUrl: String = "",
    val operatorNotes: String = "",
    val isExpanded: Boolean = false,
) {
    companion object {
        fun fromDomain(data: Usage) = Usage(
            id = data.id,
            meterStart = data.meterStart,
            meterEnd = data.meterEnd,
            amount = data.amount,
            periodMonth = data.date.toMonthFull(),
            periodYear = data.date.toYear().toInt(),
            imageUrl = data.evidenceImageUrl,
            operatorNotes = data.operatorNotes ?: "",
            isExpanded = false,
        )
    }
}