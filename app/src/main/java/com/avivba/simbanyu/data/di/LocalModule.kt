package com.avivba.simbanyu.data.di

import androidx.datastore.dataStore
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManagerImpl
import com.avivba.simbanyu.data.local.tokenmanager.TokenDataSerializer
import com.avivba.simbanyu.data.local.userdetail.LocalUserSerializer
import com.avivba.simbanyu.data.local.userdetail.LocalUserService
import com.avivba.simbanyu.data.local.userdetail.LocalUserServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    single {
        dataStore("tokens", TokenDataSerializer)
    }
    singleOf(::LocalTokenManagerImpl) { bind<LocalTokenManager>() }

    single {
        dataStore("user", LocalUserSerializer)
    }
    singleOf(::LocalUserServiceImpl) { bind<LocalUserService>() }
}