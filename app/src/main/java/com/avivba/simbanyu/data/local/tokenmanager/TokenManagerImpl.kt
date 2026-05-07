package com.avivba.simbanyu.data.local.tokenmanager

import android.content.Context

class TokenManagerImpl(
    private val context: Context
) : TokenManager {
    private val preferences = context.getSharedPreferences("tokens", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    override fun getAccessToken(): String? {
        return preferences.getString(PreferenceKeys.ACCESS_TOKEN.name, null)
    }

    override fun getRefreshToken(): String? {
        return preferences.getString(PreferenceKeys.REFRESH_TOKEN.name, null)
    }

    override fun saveAccessToken(token: String) {
        editor.putString(PreferenceKeys.ACCESS_TOKEN.name, token)
    }

    override fun saveRefreshToken(token: String) {
        editor.putString(PreferenceKeys.REFRESH_TOKEN.name, token)
    }

    override fun clearToken() {
        for (key in PreferenceKeys.entries) {
            editor.putString(key.name, null)
        }
    }

    private enum class PreferenceKeys {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }
}