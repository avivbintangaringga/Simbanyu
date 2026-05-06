package com.avivba.simbanyu.data.remote.model.usage

import com.avivba.simbanyu.domain.model.Usage

data class UsageRequest(
    val subscriberId: String,
    val currentMeter: Int,
    val operatorNotes: String? = null,
) {
    companion object {
        fun fromDomain(usage: Usage) = UsageRequest(
            subscriberId = usage.id,
            currentMeter = usage.meterEnd,
            operatorNotes = usage.operatorNotes,
        )
    }
}
