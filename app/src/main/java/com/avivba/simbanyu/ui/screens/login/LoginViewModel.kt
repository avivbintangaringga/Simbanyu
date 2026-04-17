package com.avivba.simbanyu.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginUiState, LoginAction, LoginEvent>(
    LoginUiState()
) {
    override fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.Login -> tryLogin()
            LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
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
                    errorMessage = "Terjadi kesalahan (test)"
                )
            }

            delay(1000)

            updateState { it.copy(isLoading = false) }
            sendEvent(LoginEvent.LoginSuccess)
        }
    }
}