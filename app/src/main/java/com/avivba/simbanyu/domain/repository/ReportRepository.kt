package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Report
import com.avivba.simbanyu.domain.model.ReportSubmission
import com.avivba.simbanyu.domain.model.ReportUpdateSubmission

interface ReportRepository {
    suspend fun getAll(): Result<List<Report>, DataError>
    suspend fun submit(report: ReportSubmission): Result<Unit, DataError>
    suspend fun handle(report: ReportUpdateSubmission): Result<Unit, DataError>
}