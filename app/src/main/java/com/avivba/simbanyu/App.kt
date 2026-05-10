package com.avivba.simbanyu

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.avivba.simbanyu.data.di.localModule
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
        createNotificationChannel()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    networkModule,
                    localModule,
                    repositoryModule
                )
            )
        }
    }

    private fun createNotificationChannel() {
        val name = "Default"
        val descriptionText = "Default channel for Simbanyu app"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("simbanyu_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}