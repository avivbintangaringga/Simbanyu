package com.avivba.simbanyu.data.remote.model.usage

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
    companion object {
    }
}