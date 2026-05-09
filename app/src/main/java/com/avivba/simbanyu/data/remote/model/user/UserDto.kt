package com.avivba.simbanyu.data.remote.model.user

import androidx.annotation.Keep
import com.avivba.simbanyu.data.di.BASE_URL
import com.avivba.simbanyu.domain.model.User

@Keep
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
    fun toDomain() = User(
        id = id,
        username = username,
        name = name,
        role = User.UserRole.valueOf(role),
        phoneNumber = phoneNumber,
        photoImageUrl = photoImageUrl?.let { "$BASE_URL$it" } ?: "",
    )
}