package com.avivba.simbanyu.domain.model

import java.time.Instant

data class Announcement(
    val id: String,
    val title: String,
    val body: String,
    val date: Instant
)