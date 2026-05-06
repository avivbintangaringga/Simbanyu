package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.ReportService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Report
import com.avivba.simbanyu.domain.model.ReportSubmission
import com.avivba.simbanyu.domain.model.ReportUpdateSubmission
import com.avivba.simbanyu.domain.repository.ReportRepository

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

    override suspend fun submit(report: ReportSubmission): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun handle(report: ReportUpdateSubmission): Result<Unit, DataError> {
        TODO("Not yet implemented")
    }
}