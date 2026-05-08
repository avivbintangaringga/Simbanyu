package com.avivba.simbanyu.data.di

import com.avivba.simbanyu.data.remote.api.ActivityService
import com.avivba.simbanyu.data.remote.api.AnnouncementService
import com.avivba.simbanyu.data.remote.api.AuthService
import com.avivba.simbanyu.data.remote.api.BillService
import com.avivba.simbanyu.data.remote.api.FcmTokenService
import com.avivba.simbanyu.data.remote.api.NotificationService
import com.avivba.simbanyu.data.remote.api.PaymentService
import com.avivba.simbanyu.data.remote.api.ReportService
import com.avivba.simbanyu.data.remote.api.SubscriberService
import com.avivba.simbanyu.data.remote.api.UsageService
import com.avivba.simbanyu.data.remote.api.UserService
import com.avivba.simbanyu.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://simbanyu.avivba.com"
const val API_URL = "$BASE_URL/api/"

val networkModule = module {
    single { AuthInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ActivityService::class.java) }
    single { get<Retrofit>().create(FcmTokenService::class.java) }
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