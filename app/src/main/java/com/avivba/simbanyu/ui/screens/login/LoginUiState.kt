package com.avivba.simbanyu.ui.screens.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginUiState(
    val username: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

sealed interface LoginAction {
    data object Login : LoginAction
    data object TogglePasswordVisibility: LoginAction
}

sealed interface LoginEvent {
    data object GoToSubscriberDashboard : LoginEvent
    data object GoToOperatorDashboard : LoginEvent

    data class ShowToast(val message: String) : LoginEvent
}
