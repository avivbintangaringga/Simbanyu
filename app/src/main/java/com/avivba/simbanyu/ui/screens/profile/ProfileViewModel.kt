package com.avivba.simbanyu.ui.screens.profile

import com.avivba.simbanyu.ui.shared.base.BaseViewModel

class ProfileViewModel : BaseViewModel<ProfileUiState, ProfileAction, ProfileEvent>(
    ProfileUiState()
) {
    init {
        getProfile()
    }

    override fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.Logout -> {
                // TODO: Implement logout logic

                sendEvent(ProfileEvent.GoToLogin)
            }
        }
    }

    private fun getProfile() {
        updateState {
            it.copy(
                name = "John Doe",
                meterId = "DX920130",
                phone = "08123412312",
                address = "Jl. Raya No. 123",
                category = "Rumah Tangga"
            )
        }
    }
}