package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Report

interface ReportRepository {
    suspend fun getAll(): Result<List<Report>, DataError>
    suspend fun getBySubscriberId(subscriberId: String): Result<List<Report>, DataError>
    suspend fun send(report: Report): Result<Unit, DataError>
    suspend fun update(report: Report): Result<Unit, DataError>
}