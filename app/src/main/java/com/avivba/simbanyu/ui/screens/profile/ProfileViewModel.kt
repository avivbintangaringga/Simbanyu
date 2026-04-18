package com.avivba.simbanyu.ui.screens.profile

import com.avivba.simbanyu.ui.base.BaseViewModel

class ProfileViewModel : BaseViewModel<ProfileUiState, ProfileAction, ProfileEvent>(
    ProfileUiState()
) {
    init {
        getProfile()
    }

    override fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.Logout -> {

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
            )
        }
    }
}