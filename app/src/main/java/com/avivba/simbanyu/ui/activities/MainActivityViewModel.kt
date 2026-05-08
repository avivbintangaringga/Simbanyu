package com.avivba.simbanyu.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Profile
import com.avivba.simbanyu.domain.repository.AuthEventBus
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val authEventBus: AuthEventBus
) : ViewModel() {
    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()

    val authEvents = authEventBus.events

    val isLoggedIn = authRepository.isLoggedInFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _isCheckingAuth.value = true
                val loggedIn = authRepository.isLoggedIn()
                Log.d("SDKSDK", "IsLoggedIn: ${loggedIn}")

                if (loggedIn) {
                    refreshProfile()
                }
            } finally {
                _isCheckingAuth.value = false
            }
        }
    }

    private suspend fun refreshProfile() {
        when (val profileResult = userRepository.getLocalProfile()) {
            is Result.Success -> {
                Log.d("SDKSDK", profileResult.data.toString())
                _profile.value = profileResult.data
            }

            is Result.Error -> {
                Log.d("SDKSDK", profileResult.error.toString())
            }
        }
    }
}