package com.avivba.simbanyu.data.remote.model.usage

import androidx.annotation.Keep
import com.avivba.simbanyu.domain.model.UsageUpdateSubmission

@Keep
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
