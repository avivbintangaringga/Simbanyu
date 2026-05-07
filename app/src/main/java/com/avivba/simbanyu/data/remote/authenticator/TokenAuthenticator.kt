package com.avivba.simbanyu.data.remote.authenticator

import com.avivba.simbanyu.data.local.tokenmanager.LocalTokenManager
import com.avivba.simbanyu.data.remote.api.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

//class TokenAuthenticator(
//    private val localTokenManager: LocalTokenManager,
//    private val authService: AuthService
//
//) : Authenticator {
//    override fun authenticate(route: Route?, response: Response): Request {
//        synchronized(this) {
//
//        }
//    }
//
//    private fun isRefreshNeeded(response: Response): Boolean {
//
//    }
//
//    private fun getUpdatedToken() {
//
//    }
//}