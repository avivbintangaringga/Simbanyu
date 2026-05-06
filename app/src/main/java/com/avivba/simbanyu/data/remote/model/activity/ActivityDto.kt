package com.avivba.simbanyu.data.remote.model.activity

import com.avivba.simbanyu.domain.model.Activity
import java.time.Instant

data class ActivityDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain(): Activity =
        Activity(
            id = this.id,
            title = this.title,
            body = this.body,
            date = Instant.parse(this.createdAt)
        )
}