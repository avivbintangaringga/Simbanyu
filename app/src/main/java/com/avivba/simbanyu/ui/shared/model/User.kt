package com.avivba.simbanyu.ui.shared.model

data class User(
    val id: String,
    val name: String,
    val role: String,
    val imageUrl: String,
) {
    companion object {
        fun fromDomain(user: com.avivba.simbanyu.domain.model.User): User {
            return User(
                id = user.id,
                name = user.name,
                role = user.role.name,
                imageUrl = user.photoImageUrl,
            )
        }
    }
}