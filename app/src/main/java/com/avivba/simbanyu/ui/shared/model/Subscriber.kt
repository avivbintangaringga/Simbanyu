package com.avivba.simbanyu.ui.shared.model

data class Subscriber(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val coordinate: Coordinate,
    val category: String,
    val meterId: String,
    val lastMeterRecordDate: String,
    val isRecorded: Boolean,
    val imageUrl: String,
)

