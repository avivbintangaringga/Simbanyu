package com.avivba.simbanyu.domain.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthEventBus {
    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: AuthEvent) {
        _events.emit(event)
    }
}

enum class AuthEvent {
    SESSION_EXPIRED
}
