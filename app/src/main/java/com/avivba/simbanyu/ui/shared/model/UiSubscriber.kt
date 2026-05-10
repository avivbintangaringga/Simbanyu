package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.utils.toFullDateTime

data class UiSubscriber(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val coordinate: UiCoordinate,
    val plan: String,
    val meterId: String,
    val lastMeterRecordDate: String,
    val isRecorded: Boolean,
    val imageUrl: String,
) {
    companion object {
        fun fromDomain(domain: com.avivba.simbanyu.domain.model.Subscriber): UiSubscriber {
            return UiSubscriber(
                id = domain.id,
                name = domain.user.name,
                address = domain.address,
                phone = domain.user.phoneNumber,
                coordinate = UiCoordinate(
                    latitude = domain.location.latitude,
                    longitude = domain.location.longitude
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