package com.avivba.simbanyu.domain.model

data class Profile(
    val id: String,
    val name: String,
    val username: String,
    val phoneNumber: String,
    val role: String,
    val subscriberId: String?,
    val meterId: String?,
    val plan: String?,
    val photoImageUrl: String,
)
