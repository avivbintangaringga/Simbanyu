package com.avivba.simbanyu.data.repository

import com.avivba.simbanyu.data.remote.api.AnnouncementService
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Announcement
import com.avivba.simbanyu.domain.model.AnnouncementSubmission
import com.avivba.simbanyu.domain.repository.AnnouncementRepository

class AnnouncementRepositoryImpl(
    private val announcementService: AnnouncementService
) : AnnouncementRepository {
    override suspend fun getAllAnnouncements(): Result<List<Announcement>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAnnouncement(announcement: Announcement): Result<Announcement, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun addAnnouncement(announcement: AnnouncementSubmission): Result<Announcement, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAnnouncement(announcement: Announcement): Result<Announcement, DataError> {
        TODO("Not yet implemented")
    }
}