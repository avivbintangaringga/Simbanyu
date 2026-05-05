package com.avivba.simbanyu.api.features.bill.model

data class BillDto(
    val id: String,
    val title: String,
    val amount: Double,
    val status: String,
    val items: List<com.avivba.simbanyu.data.remote.model.bill.BillItemDto>,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}