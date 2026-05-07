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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    private val _profile = MutableStateFlow<Profile?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()
    val profile = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            val loggedIn = authRepository.isLoggedIn()
            Log.d("SDKSDK", "isLoggedIn: ${isLoggedIn.value}")

            if (loggedIn) {
                when (val profileResult = userRepository.getLocalProfile()) {
                    is Result.Success -> {
                        _isLoggedIn.update { true }
                        _profile.update { profileResult.data }
                    }

                    is Result.Error -> {
                        _isLoggedIn.update { false }
                    }
                }
            }

        }
    }
}