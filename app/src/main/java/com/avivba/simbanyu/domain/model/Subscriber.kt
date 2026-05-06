package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Subscriber(
    val id: String,
    val meterId: String,
    val plan: String,
    val address: String,
    val location: Coordinate,
    val isRecorded: Boolean,
    val lastRecordedAt: Instant,
    val user: User,
)