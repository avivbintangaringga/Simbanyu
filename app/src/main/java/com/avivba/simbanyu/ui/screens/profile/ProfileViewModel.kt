package com.avivba.simbanyu.ui.screens.profile

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
            getProfile()
        }

        viewModelScope.launch {
            refreshEventBus.refreshEvent.collect {
                getProfile()
            }
        }
    }

    override fun onAction(action: ProfileAction) {
        viewModelScope.launch {
            when (action) {
                ProfileAction.Logout -> {
                    authRepository.logout()
                    sendEvent(ProfileEvent.GoToLogin)
                }
            }
        }
    }

    private suspend fun getProfile() {
        when (val profile = userRepository.getLocalProfile()) {
            is Result.Success -> {
                val data = profile.data
                updateState {
                    it.copy(
                        name = data.name,
                        meterId = data.meterId,
                        phone = data.phoneNumber,
                        address = data.address,
                        category = if (data.role == User.UserRole.SUBSCRIBER.name)
                            (data.plan ?: "SUBSCRIBER") else "OPERATOR",
                        coordinate = if (data.latitude != null && data.longitude != null) UiCoordinate(
                            data.latitude,
                            data.longitude
                        ) else null
                    )
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}