package com.avivba.simbanyu.data.local.userdetail

import androidx.annotation.Keep
import com.avivba.simbanyu.domain.model.Profile
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LocalUser(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val role: String = "",
    val photoImageUrl: String = "",
    
    val subscriberId: String? = null,
    val meterId: String? = null,
    val plan: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
) {
    fun toDomain() = Profile(
        id = id,
        name = name,
        username = username,
        phoneNumber = phoneNumber,
        role = role,
        photoImageUrl = photoImageUrl,
        subscriberId = subscriberId,
        meterId = meterId,
        plan = plan,
        address = address,
        latitude = latitude,
        longitude = longitude,
    )
}
