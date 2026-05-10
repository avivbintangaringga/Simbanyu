package com.avivba.simbanyu.ui.shared.model

data class UiUser(
    val id: String,
    val name: String,
    val role: String,
    val imageUrl: String,
) {
    companion object {
        fun fromDomain(user: com.avivba.simbanyu.domain.model.User): UiUser {
            return UiUser(
                id = user.id,
                name = user.name,
                role = user.role.name,
                imageUrl = user.photoImageUrl,
            )
        }
    }
}