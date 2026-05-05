package com.avivba.simbanyu.data.remote.model.announcement

data class AnnouncementDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}