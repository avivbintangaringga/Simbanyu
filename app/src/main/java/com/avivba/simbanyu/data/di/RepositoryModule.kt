package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.repository.*
import com.avivba.simbanyu.domain.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
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
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
}