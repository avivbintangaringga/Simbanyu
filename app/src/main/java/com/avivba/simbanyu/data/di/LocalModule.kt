package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.local.notification.LocalNotificationService
import com.avivba.simbanyu.data.local.notification.LocalNotificationServiceImpl
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManagerImpl
import com.avivba.simbanyu.data.local.userdetail.LocalUserService
import com.avivba.simbanyu.data.local.userdetail.LocalUserServiceImpl
import org.koin.dsl.module

val localModule = module {
    single<LocalTokenManager> { LocalTokenManagerImpl(get()) }
    single<LocalUserService> { LocalUserServiceImpl(get()) }
    single<LocalNotificationService> { LocalNotificationServiceImpl(get()) }
}