package com.avivba.simbanyu.ui.shared.model

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val isRead: Boolean
)
