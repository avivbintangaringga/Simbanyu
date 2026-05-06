package com.avivba.simbanyu.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
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

    private fun tryLogin() {
        viewModelScope.launch {
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
                    when (result.error) {
                        is DataError.NetworkError -> {
                            sendEvent(LoginEvent.ShowToast("Terjadi kesalahan jaringan"))
                        }
                        is DataError.AuthError -> {
                            updateState { it.copy(errorMessage = "Username atau password salah") }
                        }
                    }
                }
            }


//            if (state.value.username.text.toString() != "a") {
//                sendEvent(LoginEvent.GoToSubscriberDashboard)
//            } else {
//                sendEvent(LoginEvent.GoToOperatorDashboard)
//            }
        }
    }
}