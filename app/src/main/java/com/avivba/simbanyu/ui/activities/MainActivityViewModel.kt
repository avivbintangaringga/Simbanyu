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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val authEventBus: AuthEventBus
) : ViewModel() {
    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()

    val authEvents = authEventBus.events

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()


    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.isLoggedInFlow().collect { loggedIn ->
                if (loggedIn && !_isLoggedIn.value) {
                    _isCheckingAuth.value = true
                }
                _isLoggedIn.value = loggedIn
                if (!loggedIn) {
                    _profile.value = null
                    _isCheckingAuth.value = false
                }
            }
        }

        viewModelScope.launch {
            userRepository.getLocalProfileFlow().collect { profile ->
                _profile.value = profile
                if (profile != null && _isLoggedIn.value) {
                    _isCheckingAuth.value = false
                }
            }
        }
    }
}