package com.avivba.simbanyu.data.remote.model.bill

data class BillItemDto(
    val id: String,
    val name: String,
    val amount: Double,
) {
    companion object {
    }
}