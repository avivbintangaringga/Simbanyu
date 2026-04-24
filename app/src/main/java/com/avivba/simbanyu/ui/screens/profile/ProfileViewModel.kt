package com.avivba.simbanyu.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.Coordinate
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel<ProfileUiState, ProfileAction, ProfileEvent>(
    ProfileUiState()
) {
    init {
        viewModelScope.launch {
            getProfile()
        }
    }

    override fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            when (action) {
                ProfileAction.Logout -> {
                    // TODO: Implement logout logic

                    sendEvent(ProfileEvent.GoToLogin)
                }
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
                category = "Rumah Tangga",
                coordinate = Coordinate(
                    -7.911178168148756,
                    110.69031351790268
                )
            )
        }
    }
}