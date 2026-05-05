package com.avivba.simbanyu.data.remote.model.user

data class UserDto(
    val id: String,
    val username: String,
    val name: String,
    val role: String,
    val phoneNumber: String,
    val photoImageUrl: String? = null,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}