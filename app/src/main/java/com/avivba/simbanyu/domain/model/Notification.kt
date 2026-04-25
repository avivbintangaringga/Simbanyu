package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val date: Instant
)