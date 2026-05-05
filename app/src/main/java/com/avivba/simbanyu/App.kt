package com.avivba.simbanyu

import android.app.Application
import com.avivba.simbanyu.data.di.networkModule
import com.avivba.simbanyu.data.di.repositoryModule
import com.avivba.simbanyu.di.appModule
import com.avivba.simbanyu.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}