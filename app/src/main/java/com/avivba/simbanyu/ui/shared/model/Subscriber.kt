package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toFullDateTime

data class Subscriber(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val coordinate: Coordinate,
    val plan: String,
    val meterId: String,
    val lastMeterRecordDate: String,
    val isRecorded: Boolean,
    val imageUrl: String,
) {
    companion object {
        fun fromDomain(domain: com.avivba.simbanyu.domain.model.Subscriber): Subscriber {
            return Subscriber(
                id = domain.id,
                name = domain.user.name,
                address = domain.address,
                phone = domain.user.phoneNumber,
                coordinate = Coordinate(
                    lat = domain.location.latitude,
                    lng = domain.location.longitude
                ),
                plan = domain.plan,
                meterId = domain.meterId,
                lastMeterRecordDate = domain.lastRecordedAt?.toFullDateTime() ?: "-",
                isRecorded = domain.isRecorded,
                imageUrl = domain.user.photoImageUrl,
            )
        }
    }
}