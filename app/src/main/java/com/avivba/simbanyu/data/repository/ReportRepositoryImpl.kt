package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.ReportService
import com.avivba.simbanyu.data.remote.model.report.ReportStatus
import com.avivba.simbanyu.data.remote.model.report.ReportSubmissionDto
import com.avivba.simbanyu.data.remote.model.report.ReportUpdateSubmissionDto
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Report
import com.avivba.simbanyu.domain.model.ReportSubmission
import com.avivba.simbanyu.domain.model.ReportUpdateSubmission
import com.avivba.simbanyu.domain.repository.ReportRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ReportRepositoryImpl(
    private val reportService: ReportService
) : ReportRepository {
    override suspend fun getAll(): Result<List<Report>, DataError> {
        return safeApiCall {
            reportService.getAll().data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun submit(report: ReportSubmission): Result<Report, DataError> {
        val jsonData = Gson().toJson(
            ReportSubmissionDto(
                address = report.address,
                description = report.description,
                latitude = report.coordinate?.latitude,
                longitude = report.coordinate?.longitude
            )
        )

        val data = jsonData.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        val imagePart = MultipartBody.Part.create(report.evidenceImage.asRequestBody())

        return safeApiCall {
            reportService.add(
                data = data,
                image = imagePart
            ).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }

    override suspend fun handle(
        id: String,
        report: ReportUpdateSubmission
    ): Result<Report, DataError> {
        val jsonData = Gson().toJson(
            ReportUpdateSubmissionDto(
                status = ReportStatus.valueOf(report.status.name),
                operatorNotes = report.operatorNotes
            )
        )

        val data = jsonData.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        val imagePart = MultipartBody.Part.create(report.handlingEvidenceImage.asRequestBody())

        return safeApiCall {
            reportService.handleReport(
                id = id,
                data = data,
                image = imagePart
            ).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }
}