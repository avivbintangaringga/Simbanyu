package com.avivba.simbanyu.data.remote.model.notification

data class NotificationDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}