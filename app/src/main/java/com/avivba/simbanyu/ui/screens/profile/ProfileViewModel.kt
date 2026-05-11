package com.avivba.simbanyu.ui.screens.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.User
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.RefreshEventBus
import com.avivba.simbanyu.domain.repository.UserRepository
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val refreshEventBus: RefreshEventBus
) : BaseViewModel<ProfileUiState, ProfileAction, ProfileEvent>(
    ProfileUiState()
) {
    init {
        viewModelScope.launch {
            userRepository.getLocalProfileFlow().collect { profile ->
                if (profile != null) {
                    updateState {
                        it.copy(
                            name = profile.name,
                            meterId = profile.meterId,
                            phone = profile.phoneNumber,
                            address = profile.address,
                            category = if (profile.role == User.UserRole.SUBSCRIBER.name)
                                (profile.plan ?: "SUBSCRIBER") else "OPERATOR",
                            coordinate = if (profile.latitude != null && profile.longitude != null) UiCoordinate(
                                profile.latitude,
                                profile.longitude
                            ) else null
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                // Profile will be updated via getLocalProfileFlow
            }
        }
    }

    override fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            when (action) {
                ProfileAction.Logout -> {
                    when (authRepository.logout()) {
                        is Result.Success -> {
                            sendEvent(ProfileEvent.GoToLogin)
                        }

                        is Result.Error -> {
                            Log.e("SDKSDK", "Failed to logout")
                        }
                    }
                }
            }
        }
    }
}