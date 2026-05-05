package com.avivba.simbanyu.data.remote.model.usage

data class UsageRequest(
    val subscriberId: String,
    val currentMeter: Int,
    val operatorNotes: String? = null,
)
