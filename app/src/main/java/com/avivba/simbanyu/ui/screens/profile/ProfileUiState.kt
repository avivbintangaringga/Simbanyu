package com.avivba.simbanyu.ui.screens.profile

import com.avivba.simbanyu.ui.shared.model.Coordinate

data class ProfileUiState(
    val name: String = "",
    val meterId: String = "",
    val phone: String = "",
    val address: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val coordinate: Coordinate? = null,
)

sealed interface ProfileAction {
    object Logout : ProfileAction
}

sealed interface ProfileEvent {
    object GoToLogin : ProfileEvent
}