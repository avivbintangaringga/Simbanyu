package com.avivba.simbanyu.ui.screens.profile

import com.avivba.simbanyu.ui.shared.model.UiCoordinate

data class ProfileUiState(
    val name: String = "",
    val meterId: String? = null,
    val phone: String = "",
    val address: String? = null,
    val imageUrl: String = "",
    val category: String = "",
    val coordinate: UiCoordinate? = null,
)

sealed interface ProfileAction {
    object Logout : ProfileAction
}

sealed interface ProfileEvent {
    object GoToLogin : ProfileEvent
}