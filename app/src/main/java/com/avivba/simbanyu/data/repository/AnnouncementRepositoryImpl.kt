package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.AnnouncementService
import com.avivba.simbanyu.data.utils.safeApiCall
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Announcement
import com.avivba.simbanyu.domain.model.AnnouncementSubmission
import com.avivba.simbanyu.domain.repository.AnnouncementRepository

class AnnouncementRepositoryImpl(
    private val announcementService: AnnouncementService
) : AnnouncementRepository {
    override suspend fun getAllAnnouncements(): Result<List<Announcement>, DataError> {
        return safeApiCall {
            announcementService.getAll().data?.map {
                it.toDomain()
            } ?: emptyList()
        }
    }

    override suspend fun deleteAnnouncement(id: String): Result<Unit, DataError> {
        return safeApiCall {
            announcementService.delete(id)
        }
    }

    override suspend fun addAnnouncement(announcement: AnnouncementSubmission): Result<Announcement, DataError> {
        return safeApiCall {
            announcementService.add(announcement).data?.toDomain()
                ?: throw Exception("Invalid response")
        }
    }

    override suspend fun updateAnnouncement(
        id: String,
        announcement: AnnouncementSubmission
    ): Result<Announcement, DataError> {
        return safeApiCall {
            announcementService.update(id, announcement).data?.toDomain()
                ?: throw Exception("Invalid response")
        }
    }
}