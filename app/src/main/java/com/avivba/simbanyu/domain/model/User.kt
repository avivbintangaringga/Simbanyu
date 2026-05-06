package com.avivba.simbanyu.domain.model

data class User(
    val id: String,
    val username: String,
    val name: String,
    val phoneNumber: String,
    val photoImageUrl: String,
    val role: UserRole
) {
    enum class UserRole {
        OPERATOR,
        SUBSCRIBER,
        ADMIN
    }
}