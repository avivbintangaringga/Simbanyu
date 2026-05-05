package com.avivba.simbanyu.data.remote.model.activity

data class ActivityDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}