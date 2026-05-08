package com.avivba.simbanyu.data.remote.model.usage

import com.avivba.simbanyu.domain.model.UsageSubmission

data class UsageRequest(
    val subscriberId: String,
    val currentMeter: Int,
    val operatorNotes: String? = null,
) {
    companion object {
        fun fromDomain(usage: UsageSubmission) = UsageRequest(
            subscriberId = usage.subscriberId,
            currentMeter = usage.currentMeter,
            operatorNotes = usage.operatorNotes,
        )
    }
}
