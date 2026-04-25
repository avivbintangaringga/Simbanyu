package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Report
import com.avivba.simbanyu.domain.model.ReportSubmission
import com.avivba.simbanyu.domain.model.ReportUpdateSubmission

interface ReportRepository {
    suspend fun getAll(): Result<List<Report>, DataError>
    suspend fun getBySubscriberId(subscriberId: String): Result<List<Report>, DataError>
    suspend fun send(report: ReportSubmission): Result<Unit, DataError>
    suspend fun update(report: ReportUpdateSubmission): Result<Unit, DataError>
}