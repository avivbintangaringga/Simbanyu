package com.avivba.simbanyu.domain.repository

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Announcement

interface AnnouncementRepository {
    suspend fun getAllAnnouncements(): Result<List<Announcement>, DataError>
    suspend fun deleteAnnouncement(announcement: Announcement): Result<Announcement, DataError>
    suspend fun upsertAnnouncement(announcement: Announcement): Result<Announcement, DataError>
}