package com.avivba.simbanyu.data.remote.model.report

data class ReportDto(
    val id: String,
    val address: String,
    val description: String,
    val status: String,
    val evidenceImageUrl: String,
    val operatorNotes: String? = null,
    val handlingEvidenceImageUrl: String? = null,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
    }
}