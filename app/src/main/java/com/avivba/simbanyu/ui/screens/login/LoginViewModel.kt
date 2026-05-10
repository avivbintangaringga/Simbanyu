package com.avivba.simbanyu.ui.screens.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.FcmTokenRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val fcmTokenRepository: FcmTokenRepository
) : BaseViewModel<LoginUiState, LoginAction, LoginEvent>(
    LoginUiState()
) {
    override fun onAction(action: LoginAction) {
        viewModelScope.launch {
            when (action) {
                LoginAction.Login -> tryLogin()
                LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
            }
        }
    }

    private fun togglePasswordVisibility() {
        updateState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private suspend fun tryLogin() {
        if (state.value.isLoading) return

        updateState {
            it.copy(
                isLoading = true,
                errorMessage = ""
            )
        }

        val result = authRepository.login(
            username = state.value.username.text.toString(),
            password = state.value.password.text.toString()
        )

        updateState { it.copy(isLoading = false) }

        when (result) {
            is Result.Success -> {
                sendFcmToken()
                when (result.data.role) {
                    User.UserRole.SUBSCRIBER -> {
                        sendEvent(LoginEvent.GoToSubscriberDashboard)
                    }

                    User.UserRole.OPERATOR -> {
                        sendEvent(LoginEvent.GoToOperatorDashboard)
                    }

                    User.UserRole.ADMIN -> {
                        sendEvent(LoginEvent.ShowToast("Login menggunakan akun admin belum didukung"))
                    }
                }
            }

            is Result.Error -> {
                if (result.error == DataError.NetworkError.UNAUTHORIZED) {
                    updateState { it.copy(errorMessage = "Username atau password salah") }
                } else {
                    sendEvent(LoginEvent.ShowToast("Terjadi kesalahan, mohon periksa koneksi internet dan coba lagi"))
                }
            }
        }
    }

    private suspend fun sendFcmToken() {
        try {
            val token = FirebaseMessaging.getInstance().token.await()
            withContext(NonCancellable) {
                when (fcmTokenRepository.save(token)) {
                    is Result.Success -> {
                        Log.d("LoginViewModel", "FCM token sent successfully")
                    }

                    is Result.Error -> {
                        Log.e("LoginViewModel", "Failed to send FCM token")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Failed to get FCM token", e)
        }
    }
}