package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.repository.*
import com.avivba.simbanyu.domain.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::BillRepositoryImpl) { bind<BillRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::FcmTokenRepositoryImpl) { bind<FcmTokenRepository>() }
    singleOf(::UsageRepositoryImpl) { bind<UsageRepository>() }
    singleOf(::ReportRepositoryImpl) { bind<ReportRepository>() }
    singleOf(::ActivityRepositoryImpl) { bind<ActivityRepository>() }
    singleOf(::SubscriberRepositoryImpl) { bind<SubscriberRepository>() }
    singleOf(::AnnouncementRepositoryImpl) { bind<AnnouncementRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }
}