package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.local.tokenmanager.TokenManager
import com.avivba.simbanyu.data.local.tokenmanager.TokenManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    singleOf(::TokenManagerImpl) { bind<TokenManager>() }
}