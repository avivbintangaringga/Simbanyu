package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.repository.ActivityRepositoryImpl
import com.avivba.simbanyu.data.repository.AnnouncementRepositoryImpl
import com.avivba.simbanyu.data.repository.AuthRepositoryImpl
import com.avivba.simbanyu.data.repository.BillRepositoryImpl
import com.avivba.simbanyu.data.repository.FcmTokenRepositoryImpl
import com.avivba.simbanyu.data.repository.NotificationRepositoryImpl
import com.avivba.simbanyu.data.repository.ReportRepositoryImpl
import com.avivba.simbanyu.data.repository.SubscriberRepositoryImpl
import com.avivba.simbanyu.data.repository.UsageRepositoryImpl
import com.avivba.simbanyu.data.repository.UserRepositoryImpl
import com.avivba.simbanyu.domain.repository.ActivityRepository
import com.avivba.simbanyu.domain.repository.AnnouncementRepository
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.BillRepository
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import com.avivba.simbanyu.domain.repository.NotificationRepository
import com.avivba.simbanyu.domain.repository.ReportRepository
import com.avivba.simbanyu.domain.repository.SubscriberRepository
import com.avivba.simbanyu.domain.repository.UsageRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
    single<BillRepository> { BillRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<FcmTokenRepository> { FcmTokenRepositoryImpl(get()) }
    single<UsageRepository> { UsageRepositoryImpl(get()) }
    single<ReportRepository> { ReportRepositoryImpl(get()) }
    single<ActivityRepository> { ActivityRepositoryImpl(get()) }
    single<SubscriberRepository> { SubscriberRepositoryImpl(get()) }
    single<AnnouncementRepository> { AnnouncementRepositoryImpl(get()) }
    single<NotificationRepository> { NotificationRepositoryImpl(get(), get()) }
}