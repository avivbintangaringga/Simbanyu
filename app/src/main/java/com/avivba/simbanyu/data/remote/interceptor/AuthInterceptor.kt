package com.avivba.simbanyu.data.remote.interceptor

import androidx.annotation.Keep
import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

@Keep
class AuthInterceptor(
    private val tokenManager: LocalTokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = runBlocking { tokenManager.getAccessToken() }

        if (request.header("No-Auth") == "true") {
            return chain.proceed(request)
        }

        if (!token.isNullOrEmpty()) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}