package com.avivba.simbanyu.data.local.userdetail

import com.avivba.simbanyu.domain.model.Profile
import kotlinx.serialization.Serializable

@Serializable
data class LocalUser(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val role: String = "",
    val subscriberId: String? = null,
    val meterId: String? = null,
    val plan: String? = null,
    val photoImageUrl: String = "",
) {
    fun toDomain() = Profile(
        id = id,
        name = name,
        username = username,
        phoneNumber = phoneNumber,
        role = role,
        subscriberId = subscriberId,
        meterId = meterId,
        plan = plan,
        photoImageUrl = photoImageUrl,
    )
}
