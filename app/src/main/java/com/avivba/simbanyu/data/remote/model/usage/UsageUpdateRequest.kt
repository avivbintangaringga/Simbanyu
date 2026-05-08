package com.avivba.simbanyu.data.remote.model.usage

import com.avivba.simbanyu.domain.model.UsageUpdateSubmission

data class UsageUpdateRequest(
    val currentMeter: Int,
    val operatorNotes: String? = null,
) {
    companion object {
        fun fromDomain(usage: UsageUpdateSubmission) = UsageUpdateRequest(
            currentMeter = usage.currentMeter,
            operatorNotes = usage.operatorNotes,
        )
    }
}
