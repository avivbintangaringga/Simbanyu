package com.avivba.simbanyu.data.remote.model.subscriber

data class SubscriberDto(
    val id: String,
    val meterId: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val isRecorded: Boolean = false,
    val lastRecordedAt: String? = null,
    val plan: com.avivba.simbanyu.data.remote.model.plan.PlanDto,
    val user: com.avivba.simbanyu.data.remote.model.user.UserDto,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}