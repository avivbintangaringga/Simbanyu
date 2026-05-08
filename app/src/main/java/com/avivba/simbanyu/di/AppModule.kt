package com.avivba.simbanyu.di

import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import com.avivba.simbanyu.domain.repository.AuthEventBus
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AuthEventBus() }
    single {
        val context = androidContext()
        ImageLoader.Builder(
            context = context
        ).memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }.diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }.build()
    }
}