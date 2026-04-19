package com.avivba.simbanyu.ui.screens.profile

data class ProfileUiState(
    val name: String = "",
    val meterId: String = "",
    val phone: String = "",
    val address: String = "",
    val image: String = "",
    val category: String = "",
)

sealed interface ProfileAction {
    object Logout : ProfileAction
}

sealed interface ProfileEvent {
    object GoToLogin : ProfileEvent
}