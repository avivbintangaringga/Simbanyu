package com.avivba.simbanyu.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.model.Profile
import com.avivba.simbanyu.domain.repository.AuthEventBus
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

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
            authRepository.isLoggedInFlow().collect {
                _isLoggedIn.value = it
            }
        }

        viewModelScope.launch {
            userRepository.getLocalProfileFlow().collect {
                _profile.value = it
            }
        }

        viewModelScope.launch {
            withTimeoutOrNull(2000) {
                combine(
                    authRepository.isLoggedInFlow(),
                    userRepository.getLocalProfileFlow()
                ) { loggedIn, profile ->
                    loggedIn to profile
                }.filter { (loggedIn, profile) ->
                    !loggedIn || profile != null
                }.first()
            }
            _isCheckingAuth.value = false
        }
    }
}