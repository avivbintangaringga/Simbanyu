package com.avivba.simbanyu.domain.model

data class Subscriber(
    val id: String,
    val meterId: String,
    val subscriberType: SubscriberType,
    val address: String,
    val location: Coordinate,
    val user: User
) {
    enum class SubscriberType {
        SOCIAL,
        RESIDENTIAL,
        COMMERCIAL
    }
}