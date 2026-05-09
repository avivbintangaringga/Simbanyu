package com.avivba.simbanyu.data.remote.model.report

import androidx.annotation.Keep
import com.avivba.simbanyu.data.di.BASE_URL
import com.avivba.simbanyu.data.remote.model.subscriber.SubscriberDto
import com.avivba.simbanyu.domain.model.Coordinate
import com.avivba.simbanyu.domain.model.Report
import java.time.Instant

@Keep
data class ReportDto(
    val id: String,
    val address: String,
    val latitude: Double?,
    val longitude: Double?,
    val description: String,
    val status: String,
    val evidenceImageUrl: String,
    val operatorNotes: String? = null,
    val handlingEvidenceImageUrl: String? = null,
    val subscriber: SubscriberDto,
    val createdAt: String,
    val updatedAt: String,
) {
    fun toDomain() = Report(
        id = id,
        address = address,
        description = description,
        status = Report.ReportStatus.valueOf(status),
        evidenceImageUrl = "$BASE_URL$evidenceImageUrl",
        operatorNotes = operatorNotes ?: "",
        handlingEvidenceImageUrl = handlingEvidenceImageUrl?.let { "$BASE_URL$it" } ?: "",
        date = Instant.parse(createdAt),
        subscriber = subscriber.toDomain(),
        coordinate = if (latitude != null && longitude != null) {
            Coordinate(latitude, longitude)
        } else {
            null
        }
    )
}