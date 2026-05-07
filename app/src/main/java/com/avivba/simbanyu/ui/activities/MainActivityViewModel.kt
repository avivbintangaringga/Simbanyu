package com.avivba.simbanyu.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.domain.base.Result
import com.avivba.simbanyu.domain.model.Profile
import com.avivba.simbanyu.domain.repository.AuthRepository
import com.avivba.simbanyu.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _isCheckingAuth.value = true
                val loggedIn = authRepository.isLoggedIn()
                Log.d("SDKSDK", "IsLoggedIn: ${loggedIn}")

                if (loggedIn) {
                    when (val profileResult = userRepository.getLocalProfile()) {
                        is Result.Success -> {
                            Log.d("SDKSDK", profileResult.data.toString())
                            _profile.value = profileResult.data
                            _isLoggedIn.value = true
                        }

                        is Result.Error -> {
                            Log.d("SDKSDK", profileResult.error.toString())
                            // TODO: If local profile is missing despite token, force logout or relogin
                            _isLoggedIn.value = false
                        }
                    }
                } else {
                    _isLoggedIn.value = false
                }
            } finally {
                _isCheckingAuth.value = false
            }
        }
    }
}