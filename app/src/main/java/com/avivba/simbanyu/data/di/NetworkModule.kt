package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.remote.api.*
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://simbanyu.avivba.com/api/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ActivityService::class.java) }
    single { get<Retrofit>().create(FcmService::class.java) }
    single { get<Retrofit>().create(AuthService::class.java) }
    single { get<Retrofit>().create(BillService::class.java) }
    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(UsageService::class.java) }
    single { get<Retrofit>().create(ReportService::class.java) }
    single { get<Retrofit>().create(PaymentService::class.java) }
    single { get<Retrofit>().create(SubscriberService::class.java) }
    single { get<Retrofit>().create(AnnouncementService::class.java) }
    single { get<Retrofit>().create(NotificationService::class.java) }
}