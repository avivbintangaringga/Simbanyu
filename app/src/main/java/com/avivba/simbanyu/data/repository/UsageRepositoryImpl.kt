package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.UsageService
import com.avivba.simbanyu.data.remote.model.usage.UsageRequest
import com.avivba.simbanyu.data.remote.model.usage.UsageUpdateRequest
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Usage
import com.avivba.simbanyu.domain.model.UsageSubmission
import com.avivba.simbanyu.domain.model.UsageUpdateSubmission
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UsageRepositoryImpl(
    private val usageService: UsageService
) : UsageRepository {
    override suspend fun getAll(
        subscriberId: String,
        limitLast: Int
    ): Result<List<Usage>, DataError> {
        return safeApiCall {
            usageService.getBySubscriber(
                subscriberId = subscriberId,
                limit = limitLast
            ).data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun getLastUsage(subscriberId: String): Result<Usage, DataError> {
        return safeApiCall {
            usageService.getBySubscriber(
                subscriberId = subscriberId,
                limit = 1
            ).data?.firstOrNull()?.toDomain() ?: throw Exception("Invalid response")
        }
    }

    override suspend fun recordUsage(usage: UsageSubmission): Result<Usage, DataError> {
        val jsonData = Gson().toJson(
            UsageRequest.fromDomain(usage)
        )

        val data = jsonData.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        val imagePart = MultipartBody.Part.create(usage.evidenceImage.asRequestBody())

        return safeApiCall {
            usageService.recordUsage(
                data = data,
                image = imagePart
            ).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }

    override suspend fun updateUsage(
        id: String,
        usage: UsageUpdateSubmission
    ): Result<Usage, DataError> {
        val jsonData = Gson().toJson(
            UsageUpdateRequest.fromDomain(usage)
        )

        val data = jsonData.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        val imagePart = usage.evidenceImage?.asRequestBody()?.let { MultipartBody.Part.create(it) }

        return safeApiCall {
            usageService.updateUsage(
                id = id,
                data = data,
                image = imagePart
            ).data?.toDomain() ?: throw Exception("Invalid response")
        }
    }
}