package com.avivba.simbanyu.data.remote.model.subscriber

import androidx.annotation.Keep
import com.avivba.simbanyu.data.remote.model.plan.PlanDto
import com.avivba.simbanyu.data.remote.model.user.UserDto
import com.avivba.simbanyu.domain.model.Coordinate
import com.avivba.simbanyu.domain.model.Subscriber
import java.time.Instant

@Keep
data class SubscriberDto(
    val id: String,
    val meterId: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val isRecorded: Boolean = false,
    val lastRecordedAt: String? = null,
    val plan: PlanDto,
    val user: UserDto,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain() = Subscriber(
        id = id,
        meterId = meterId,
        address = address,
        location = Coordinate(latitude, longitude),
        isRecorded = isRecorded,
        lastRecordedAt = lastRecordedAt?.let { Instant.parse(it) },
        plan = plan.name,
        user = user.toDomain()
    )
}